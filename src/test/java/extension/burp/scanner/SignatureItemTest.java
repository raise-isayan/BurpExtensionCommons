package extension.burp.scanner;

import burp.MockMontoya;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Marker;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isayan
 */
public class SignatureItemTest {

    public SignatureItemTest() {
    }

    private MockMontoya montoya;
    private MontoyaApi api;
    private MockMontoya.MockMontoyaObjectFactory mockFactory;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        this.montoya = new MockMontoya();
        this.api = montoya.api();
        this.mockFactory = this.montoya.instance(MockMontoya.MockMontoyaObjectFactory.class);
    }

    @AfterEach
    public void tearDown() {
    }


    /**
     * Test of parse method, of class SensitiveMatcher.
     */
    @Test
    public void testMarker() {
        Marker mark = Marker.marker(1024, 2058);
        System.out.println("testMarker:" + mark);
    }


    /**
     * Test of markerSortOrder method, of class SignatureScanBase.
     */
    @Test
    public void testMarkerSortOrder() {
        System.out.println("markerSortOrder");
        List<Marker> applyRequestMarkers = new ArrayList<>();
        List<Marker> applyResponseMarkers = new ArrayList<>();
        applyRequestMarkers.add(Marker.marker(1024, 2058));
        applyRequestMarkers.add(Marker.marker(124, 258));
        applyRequestMarkers.add(Marker.marker(1024, 3012));
        applyRequestMarkers.add(Marker.marker(512, 758));
        applyRequestMarkers.add(Marker.marker(312, 790));
        applyRequestMarkers.add(Marker.marker(-1, -1));
        applyRequestMarkers.add(Marker.marker(2017, 4790));
        SignatureScanBase.markerSortOrder(applyRequestMarkers, applyResponseMarkers);
        for (int i = 0; i < applyRequestMarkers.size(); i++) {
            Marker pos = applyRequestMarkers.get(i);
            System.out.println("reqst:" + pos.range().startIndexInclusive() + "\treqed:" + pos.range().endIndexExclusive());
            assertEquals(true, pos.range().startIndexInclusive() <= pos.range().endIndexExclusive());
        }
        for (int i = 0; i < applyResponseMarkers.size(); i++) {
            Marker pos = applyResponseMarkers.get(i);
            System.out.println("resst:" + pos.range().startIndexInclusive() + "\tresed:" + pos.range().endIndexExclusive());
            assertEquals(true, pos.range().startIndexInclusive() <= pos.range().endIndexExclusive());
        }

        System.out.println("========");
        {
            List<Marker> applyUnionReuestMarkers = SignatureScanBase.markerUnionRegion(applyRequestMarkers);
            Marker pre_pos = null;
            for (int i = 0; i < applyUnionReuestMarkers.size(); i++) {
                Marker pos = applyUnionReuestMarkers.get(i);
                if (pre_pos != null) {
                    assertTrue( pre_pos.range().startIndexInclusive() <= pos.range().startIndexInclusive() && pre_pos.range().startIndexInclusive() <= pos.range().endIndexExclusive());
                    assertTrue( pre_pos.range().startIndexInclusive() <= pos.range().endIndexExclusive() && pre_pos.range().endIndexExclusive() <= pos.range().endIndexExclusive());
                }
                pre_pos = pos;
            }
        }
        {
            List<Marker> applyUnionResponseMarkers = SignatureScanBase.markerUnionRegion(applyResponseMarkers);
            Marker pre_pos = null;
            for (int i = 0; i < applyUnionResponseMarkers.size(); i++) {
                Marker pos = applyUnionResponseMarkers.get(i);
                if (pre_pos != null) {
                    assertTrue(pre_pos.range().startIndexInclusive() <= pos.range().startIndexInclusive() && pre_pos.range().startIndexInclusive() <= pos.range().endIndexExclusive());
                    assertTrue(pre_pos.range().startIndexInclusive() <= pos.range().endIndexExclusive() && pre_pos.range().endIndexExclusive() <= pos.range().endIndexExclusive());
                }
                pre_pos = pos;
            }
        }
    }

}
