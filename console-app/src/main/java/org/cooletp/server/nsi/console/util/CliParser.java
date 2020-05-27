package org.cooletp.server.nsi.console.util;

import org.apache.commons.cli.*;
import org.cooletp.server.nsi.console.NsiConfig;
import org.cooletp.server.nsi.console.exception.CliParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CliParser {
    private Options optionList;
    private CommandLine cmd;

    private final NsiConfig config;

    @Autowired
    public CliParser(NsiConfig config) {
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
            if(args.length == 0) {
                throw new ParseException("Запуск без параметров запрещен");
            }

            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(getOptionList(), args);

            // Проверим что тип справочника соответствует тому, что в конфиге
            if(isTypeCommand()) {
                checkOptionsAllowed(getTypeValue());
            }
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
        return cmd.getOptionValue("t").toLowerCase();
    }

    public boolean isLoadAll() {
        return cmd.hasOption("a");
    }

    public void showHelpMessage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("nsi-console-app-1.0 <command> [param] [<command>]", getOptionList());
    }

    private void checkOptionsAllowed(String value) throws ParseException {
        if( !(value.equalsIgnoreCase(config.getAllNsi()) || config.getNsiMap().keySet().stream().anyMatch(value::equalsIgnoreCase))) {
            throw new ParseException("Выбран неверный типа NSI справочника");
        }
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
