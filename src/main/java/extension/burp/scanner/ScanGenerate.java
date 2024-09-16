package extension.burp.scanner;

import burp.api.montoya.core.Marker;
import burp.api.montoya.core.Range;
import burp.api.montoya.http.message.HttpRequestResponse;
import extension.helpers.HttpRequestWapper;
import extension.helpers.HttpResponseWapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://forum.portswigger.net/thread/reference-for-issue-details-a39a1e3a
 *
 * @author isayan
 */
public class ScanGenerate {

    private final static Comparator<Range> COMPARE_RANGE = new Comparator<>() {
        @Override
        public int compare(Range o1, Range o2) {
            if ((o1 == null || o2 == null)) {
                return 0;
            }
            int cmp = Integer.compare(o1.startIndexInclusive(), o2.startIndexInclusive());
            if (cmp == 0) {
                return Integer.compare(o2.endIndexExclusive(), o1.endIndexExclusive());
            } else {
                return cmp;
            }
        }
    };

    private final static Comparator<Marker> COMPARE_MARKER = new Comparator<>() {
        @Override
        public int compare(Marker o1, Marker o2) {
            if ((o1 == null || o2 == null)) {
                return 0;
            }
            int cmp = Integer.compare(o1.range().startIndexInclusive(), o2.range().startIndexInclusive());
            if (cmp == 0) {
                return Integer.compare(o2.range().endIndexExclusive(), o1.range().endIndexExclusive());
            } else {
                return cmp;
            }
        }
    };

    public static void rangeSortOrder(List<Range> applyRequestMarkers, List<Range> applyResponseMarkers) {
        // ソートする
        if (applyRequestMarkers != null) {
            applyRequestMarkers.sort(COMPARE_RANGE);
        }
        if (applyResponseMarkers != null) {
            applyResponseMarkers.sort(COMPARE_RANGE);
        }
    }

    public static void markerSortOrder(List<Marker> applyRequestMarkers, List<Marker> applyResponseMarkers) {
        // ソートする
        if (applyRequestMarkers != null) {
            applyRequestMarkers.sort(COMPARE_MARKER);
        }
        if (applyResponseMarkers != null) {
            applyResponseMarkers.sort(COMPARE_MARKER);
        }
    }

    public static List<Range> rangeUnionRegion(List<Range> markers) {
        // 領域が重なってる場合に除外
        // A の領域のなかに B が一部でも含まれる場合にはBを含めない
        List<Range> regions = new ArrayList<>();
        NEXT:
        for (Range mark : markers) {
            for (Range reg : regions) {
                if (reg.startIndexInclusive() <= mark.startIndexInclusive() && mark.startIndexInclusive() <= reg.endIndexExclusive()) {
                    continue NEXT;
                }
            }
            regions.add(mark);
        }
        return regions;
    }

    public static List<Marker> markerUnionRegion(List<Marker> markers) {
        // 領域が重なってる場合に除外
        // A の領域のなかに B が一部でも含まれる場合にはBを含めない
        List<Marker> regions = new ArrayList<>();
        NEXT:
        for (Marker mark : markers) {
            for (Marker reg : regions) {
                if (reg.range().startIndexInclusive() <= mark.range().startIndexInclusive() && mark.range().startIndexInclusive() <= reg.range().endIndexExclusive()) {
                    continue NEXT;
                }
            }
            regions.add(mark);
        }
        return regions;
    }

    public static HttpRequestResponse applyMarkers(HttpRequestResponse baseRequestResponse, Pattern pattern, int group, boolean isMessageIsRequest) {
        List<Marker> requestMarkers = new ArrayList<>();
        List<Marker> responseMarkers = new ArrayList<>();

        if (isMessageIsRequest) {
            HttpRequestWapper warapRequest = new HttpRequestWapper(baseRequestResponse.request());
            if (warapRequest.hasHttpRequest()) {
                Matcher m = pattern.matcher(warapRequest.getMessageString(StandardCharsets.ISO_8859_1));
                while (m.find()) {
                    requestMarkers.add(Marker.marker(Range.range(m.start(group), m.end(group))));
                }
            }
        } else {
            HttpResponseWapper wrapResponse = new HttpResponseWapper(baseRequestResponse.response());
            if (wrapResponse.hasHttpResponse()) {
                Matcher m = pattern.matcher(wrapResponse.getMessageString(StandardCharsets.ISO_8859_1));
                while (m.find()) {
                    responseMarkers.add(Marker.marker(Range.range(m.start(group), m.end(group))));
                }
            }
        }

        if (!(requestMarkers.isEmpty() || responseMarkers.isEmpty())) {
            List<Marker> markers = new ArrayList<>();
            markers.addAll(requestMarkers);
            baseRequestResponse = baseRequestResponse.withRequestMarkers(markers);
            markers.addAll(responseMarkers);
            baseRequestResponse = baseRequestResponse.withResponseMarkers(markers);
        } else if (!requestMarkers.isEmpty()) {
            baseRequestResponse = baseRequestResponse.withRequestMarkers(requestMarkers);
        } else if (!responseMarkers.isEmpty()) {
            baseRequestResponse = baseRequestResponse.withResponseMarkers(responseMarkers);
        }
        return baseRequestResponse;
    }

}
