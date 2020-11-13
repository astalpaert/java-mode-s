package aero.t2s.modes.decoder.df;

import aero.t2s.modes.Track;
import aero.t2s.modes.decoder.AltitudeEncoding;
import aero.t2s.modes.decoder.Common;
import aero.t2s.modes.decoder.Decoder;
import aero.t2s.modes.decoder.df.bds.BdsDecoder;
import org.slf4j.LoggerFactory;

public class DF20 extends DownlinkFormat {
    private final BdsDecoder bds;

    public DF20(Decoder decoder) {
        super(decoder);

        this.bds = new BdsDecoder();
    }

    @Override
    public Track decode(short[] data) {
        Track track = getDecoder().getTrack(getIcaoAddressFromParity(data));

        int flightStatus = data[0] & 0x3;
        track.getFlightStatus().setAlert(Common.isFlightStatusAlert(flightStatus));
        track.getFlightStatus().setSpi(Common.isFlightStatusSpi(flightStatus));
        track.setAltitude(AltitudeEncoding.decode((data[2] & 0x1F) << 8 | data[3]));

        if (!bds.decode(track, data)) {
            LoggerFactory.getLogger(DF21.class).warn("ADS-B: DF-20 received but could not determine BDS code {}", Common.toHexString(data));
        }

        return track;
    }
}
