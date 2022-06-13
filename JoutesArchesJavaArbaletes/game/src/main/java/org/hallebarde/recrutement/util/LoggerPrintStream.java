package org.hallebarde.recrutement.util;

import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

public class LoggerPrintStream extends PrintStream {

    private final Logger logger;

    public LoggerPrintStream(Logger logger) {
        super(System.out);
        this.logger = logger;
    }

    @Override
    public void print(boolean b) {
        this.logger.info(b);
    }

    @Override
    public void print(char c) {
        this.logger.info(c);
    }

    @Override
    public void print(int i) {
        this.logger.info(i);
    }

    @Override
    public void print(long l) {
        this.logger.info(l);
    }

    @Override
    public void print(float f) {
        this.logger.info(f);
    }

    @Override
    public void print(double d) {
        this.logger.info(d);
    }

    @Override
    public void print(char[] s) {
        this.logger.info(s);
    }

    @Override
    public void print(String s) {
        this.logger.info(s);
    }

    @Override
    public void print(Object obj) {
        this.logger.info(obj);
    }

    @Override
    public void println() {
        this.logger.info("");
    }

    @Override
    public void println(boolean x) {
        this.logger.info(x);
    }

    @Override
    public void println(char x) {
        this.logger.info(x);
    }

    @Override
    public void println(int x) {
        this.logger.info(x);
    }

    @Override
    public void println(long x) {
        this.logger.info(x);
    }

    @Override
    public void println(float x) {
        this.logger.info(x);
    }

    @Override
    public void println(double x) {
        this.logger.info(x);
    }

    @Override
    public void println(char[] x) {
        this.logger.info(x);
    }

    @Override
    public void println(String x) {
        this.logger.info(x);
    }

    @Override
    public void println(Object x) {
        this.logger.info(x);
    }

}
