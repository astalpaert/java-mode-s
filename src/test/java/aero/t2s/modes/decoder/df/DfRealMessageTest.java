package aero.t2s.modes.decoder.df;

import aero.t2s.modes.BinaryHelper;
import aero.t2s.modes.database.ModeSDatabase;
import aero.t2s.modes.decoder.Decoder;
import aero.t2s.modes.decoder.UnknownDownlinkFormatException;
import aero.t2s.modes.decoder.df.bds.Bds60;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class DfRealMessageTest {
    @Test
    public void test_bds60() throws UnknownDownlinkFormatException {
        DownlinkFormat df = testMessage("A0001838E71A21357F640110A153");

        assertInstanceOf(DF20.class, df);

        assertTrue(((DF20)df).isValid());
        assertFalse(((DF20)df).isMultipleMatches());
        assertInstanceOf(Bds60.class, ((DF20)df).getBds());
        assertEquals(38000, ((DF20) df).getAltitude().getAltitude());

        Bds60 bds = (Bds60) ((DF20)df).getBds();
        assertTrue(bds.isStatusMagneticHeading());
        assertEquals(289.863, bds.getMagneticHeading(), 0.001);
        assertTrue(bds.isStatusIas());
        assertEquals(272, bds.getIas());
        assertTrue(bds.isStatusMach());
        assertEquals(0.852, bds.getMach());
        assertTrue(bds.isStatusBaroRocd());
        assertEquals(-640, bds.getBaroRocd());
        assertTrue(bds.isStatusIrsRocd());
        assertEquals(32, bds.getIrsRocd());
    }

    private DownlinkFormat testMessage(String message) throws UnknownDownlinkFormatException {
        Decoder decoder = new Decoder(new HashMap<>(), 50, 2, ModeSDatabase.createDatabase());

        return  decoder.decode(BinaryHelper.stringToByteArray(message));
    }
}
