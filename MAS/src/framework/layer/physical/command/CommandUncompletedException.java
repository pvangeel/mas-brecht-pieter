package framework.layer.physical.command;

/**
 * Thrown when the execution instructions of a command were able to attain part
 * of the intended result, but not the entire result. However, there are no
 * side-effects so the command can safely be run again in an attempt to attain
 * the result.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class CommandUncompletedException extends Exception {

}
