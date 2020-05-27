package org.cooletp.server.nsi;

import org.cooletp.server.nsi.console.NsiConfig;
import org.cooletp.server.nsi.console.exception.CliParseException;
import org.cooletp.server.nsi.console.util.CliParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        NsiConfig.class
})
public class CliParserTest {
    private final NsiConfig config;

    private CliParser parser;

    @Autowired
    public CliParserTest(NsiConfig config) {
        this.config = config;
    }

    @BeforeEach
    void setUp() {
        parser = new CliParser(config);
        parser.init();
    }

    @AfterEach
    void tearDown() {
        parser = null;
    }

    @Test
    public void givenNoParams_whenTestingParser_thenShowErrorAndHelpMsg() {
        try {
            parser.parse();
        } catch (CliParseException ex) {
            assertThat(ex).hasMessageContaining("Запуск без параметров запрещен");
        }
    }

    @Test
    public void givenCmdParams_whenParamNotAllowed_thenThrowException() {
        try{
            parser.parse("--hulp");
        } catch (CliParseException ex) {
            assertThat(ex).hasMessageContaining("Unrecognized option: --hulp");
        }
    }

    @Test
    public void givenCmdParams_whenRequestHelp_thenSayThatIsHelpCmd() {
        parser.parse("-h");
        assertThat(parser.isHelpCommand()).isTrue();

        parser.parse("--help");
        assertThat(parser.isHelpCommand()).isTrue();
    }

    @Test
    public void givenCmdParams_whenRequestAll_thenSayThatIsAllCmd() {
        parser.parse("-a");
        assertThat(parser.isLoadAll()).isTrue();

        parser.parse("--all");
        assertThat(parser.isLoadAll()).isTrue();
    }

    @Test
    public void givenCmdParams_whenRequestType_thenSayThatIsTypeCmd() {
        parser.parse("--type", "OKATO");
        assertThat(parser.isTypeCommand()).isTrue();

        parser.parse("--type", "ALL");
        assertThat(parser.isTypeCommand()).isTrue();
    }

    @Test
    public void givenCmdParams_whenRequestTypeAndTypeWrong_thenThrowException() {
        try {
            parser.parse("-t", "nonExistent");
        } catch (CliParseException ex) {
            assertThat(ex).hasMessageContaining("Выбран неверный типа NSI справочника");
        }
    }

    @Test
    public void givenCmdParams_whenRequestCombined_thenSayThatIsCombinedCmd() {
        parser.parse("--type", "ALL", "-a");
        assertThat(parser.isTypeCommand()).isTrue();
        assertThat(parser.isLoadAll()).isTrue();
        assertThat(parser.getTypeValue()).isEqualTo("all");
    }
}
