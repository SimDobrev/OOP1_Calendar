package interfaces;

/**
 * An interface to be implemented by classes, which will be used as executables
 * with a singular function.
 */
public interface Command {
    /**
     * The method which executes every command that has implemented it.
     * @param args Has an undefined amount of arguments for each command's requirements.
     */
    void execute(String... args);
}
