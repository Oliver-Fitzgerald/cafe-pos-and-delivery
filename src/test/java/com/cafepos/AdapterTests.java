package com.cafepos;

// vendor
import vendor.legacy.LegacyThermalPrinter;
// cafepos
import com.cafepos.printing.Printer;
import com.cafepos.printing.LegacyPrinterAdapter;
// juinit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdapterTests {


    /**
     * legacyAdpaterPrinter_Test
     * Invokes a legacy printer using it's adapter and confirms that the 
     * legacy logic is still inoked using the latest interface i.e print
     */
    @Test
    void legacyAdpaterPrinter_Test() {

        FakeLegacy mock = new FakeLegacy();
        Printer printer = new LegacyPrinterAdapter(mock);

        printer.print("ABC");
        assertTrue(mock.lastLen >= 3);
    }
    /**
     * FakeLegacy
     * Mocks a fake legacy printer that we adapt in our tests
     */
    public class FakeLegacy extends LegacyThermalPrinter {

        int lastLen = 0;

        @Override
        public void legacyPrint(byte[] payload) {
            lastLen = payload.length;
        }
    }
}
