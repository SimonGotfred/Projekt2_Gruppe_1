public class ExitMenuCommand extends Exception
{   // TODO: refactor to 'AbortToMenuCommand'
    // This is a unique exception to handle cases, wherein the user aborts a function from deeply within the code.
    // Throws the user back to the menu they started from, circumventing the need for methods to each check for
    // and relay special-case return-values, that signal the function has been aborted.
}
