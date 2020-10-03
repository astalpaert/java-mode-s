package aero.t2s.modes.decoder.df.df17;

import aero.t2s.modes.Track;
import aero.t2s.modes.decoder.Common;
import org.slf4j.LoggerFactory;

public class ReservedMessage extends ExtendedSquitter {
    @Override
    public void decode(Track track, int typeCode, short[] data) {
        LoggerFactory.getLogger(ReservedMessage.class).warn(
            "Mode-S: {} Sent a reserved {}",
            track.getIcao(),
            Common.toHexString(data)
        );
    }
}
