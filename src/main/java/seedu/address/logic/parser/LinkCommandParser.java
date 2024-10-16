package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAREGIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;

import java.util.stream.Stream;

import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LinkCommand
     * and returns a LinkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PATIENT, PREFIX_CAREGIVER);

        if (!arePrefixesPresent(argMultimap, PREFIX_PATIENT, PREFIX_CAREGIVER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PATIENT, PREFIX_CAREGIVER);
        Nric patientNric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_PATIENT).get());
        Nric caregiverNric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_CAREGIVER).get());

        return new LinkCommand(patientNric, caregiverNric);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}