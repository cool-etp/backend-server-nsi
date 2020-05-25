package org.cooletp.server.nsi.console.cli;

import org.apache.commons.cli.*;
import org.cooletp.server.nsi.console.NsiConfig;
import org.cooletp.server.nsi.console.exception.CliParseException;
import org.cooletp.server.nsi.console.ftp.NsiLoaderFabric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class CliParser {
    private Options optionList;
    private CommandLine cmd;

    private final NsiLoaderFabric loaderFabric;
    private final NsiConfig config;

    @Autowired
    public CliParser(NsiLoaderFabric loaderFabric, NsiConfig config) {
        this.loaderFabric = loaderFabric;
        this.config = config;
    }

    @PostConstruct
    public void init() {
        defineAllowedOptions();
    }

    public Options getOptionList() {
        return optionList;
    }

    public void parse(String ...args) throws CliParseException {
        try {
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(getOptionList(), args);
        } catch (ParseException ex) {
            throw new CliParseException(ex.getMessage());
        }

    }

    public boolean isHelpCommand() {
        return cmd.hasOption("h");
    }

    public boolean isTypeCommand() {
        return cmd.hasOption("t");
    }

    public String getTypeValue() {
        return cmd.getOptionValue("t");
    }

    public boolean isLoadAll() {
        return cmd.hasOption("a");
    }

    public void showHelpMessage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("nsi-console-app-1.0 <command> [param] [<command>]", getOptionList());
    }

    private void defineAllowedOptions() {
        if (optionList == null) {
            optionList = new Options();
        }

        Option help = new Option("h", "help", false, "показать это сообщение");

        String typeDesc = "тип справочников для загрузки:\n" + String.join("\n", config.getAllowedNsiTypesNames());
        Option type = Option
                .builder("t")
                .longOpt("type")
                .desc(typeDesc)
                .hasArg()
                .argName("mode_name")
                .build();

        Option all = Option
                .builder("a")
                .longOpt("all")
                .desc("режим загрузки. если указан - то загружать все доступные данные для справочника" +
                        ", иначе только за предыдущий день")
                .required(false)
                .build();

        optionList.addOption(help);
        optionList.addOption(type);
        optionList.addOption(all);
    }

}
