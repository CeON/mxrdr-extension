package pl.edu.icm.pl.mxrdr.extension.workflow.step;


import org.junit.jupiter.api.Test;

import pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputParameterProcessor;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

public class XdsInputParameterProcessorTest {

    @Test
    public void shouldReplaceValues() throws IOException {
        
        
        XdsInputParameterProcessor replaceRange = XdsInputParameterProcessor.replaceUndefinedValue("OSCILLATION_RANGE", () -> {return "0.2";});
        assertThat(replaceRange.process("OSCILLATION_RANGE= 0")).isEqualTo("OSCILLATION_RANGE= 0");
        assertThat(replaceRange.process("OSCILLATION_RANGE= 0 ")).isEqualTo("OSCILLATION_RANGE= 0 ");
        assertThat(replaceRange.process("OSCILLATION_RANGE=0")).isEqualTo("OSCILLATION_RANGE=0");
        assertThat(replaceRange.process("OSCILLATION_RANGE= XXX")).isEqualTo("OSCILLATION_RANGE=0.2");
        assertThat(replaceRange.process("OSCILLATION_RANGE=XXX")).isEqualTo("OSCILLATION_RANGE=0.2");
        assertThat(replaceRange.process("OSCILLATION_RANGE= XXX ")).isEqualTo("OSCILLATION_RANGE=0.2 ");
        assertThat(replaceRange.process("OSCILLATION_RANGE= 0.1000")).isEqualTo("OSCILLATION_RANGE= 0.1000");
        assertThat(replaceRange.process("OSCILLATION_RANGE= 0.1000 ")).isEqualTo("OSCILLATION_RANGE= 0.1000 ");

        String orgsLine = "ORGX= XXX ORGY= XXX ! values form frame header: only read by XYCORR, IDXREF";
        
        XdsInputParameterProcessor replaceOrgX = XdsInputParameterProcessor.replaceUndefinedValue("ORGX", () -> {return "1224.00";});
        assertThat(replaceOrgX.process(orgsLine)).isEqualTo("ORGX=1224.00 ORGY= XXX ! values form frame header: only read by XYCORR, IDXREF");

        XdsInputParameterProcessor replaceOrgY = XdsInputParameterProcessor.replaceUndefinedValue("ORGY", () -> {return "1250.00";});
        assertThat(replaceOrgY.process(orgsLine)).isEqualTo("ORGX= XXX ORGY=1250.00 ! values form frame header: only read by XYCORR, IDXREF");
}

}
