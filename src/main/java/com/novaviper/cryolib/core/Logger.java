package com.novaviper.cryolib.core;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

/**
 *
 * @author NovaViper <nova.gamez15@gmail.com>
 */
public class Logger {

    public final String modId;

    private Logger(String id) {
        this.modId = id;
    }

    public void log(Level logLevel, Object msg) {
        FMLLog.log(this.modId, logLevel, String.valueOf(msg), new Object[0]);
    }

    public void fatal(Object msg) {
        log(Level.FATAL, msg);
    }

    public void error(Object msg) {
        log(Level.ERROR, msg);
    }

    public void warn(Object msg) {
        log(Level.WARN, msg);
    }

    public void info(Object msg) {
        log(Level.INFO, msg);
    }

    public void fine(Object msg) {
        log(Level.DEBUG, msg);
    }

    public void finer(Object msg) {
        log(Level.TRACE, msg);
    }

    public void all(Object msg) {
        log(Level.ALL, msg);
    }

    public static Logger createLogger(String modId) {
        return new Logger(modId);
    }
}