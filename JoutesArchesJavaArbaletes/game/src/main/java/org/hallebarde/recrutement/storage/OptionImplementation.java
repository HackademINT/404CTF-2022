package org.hallebarde.recrutement.storage;

import com.google.gson.stream.JsonWriter;
import org.hallebarde.recrutement.api.storage.Options;

import java.io.IOException;

import static java.lang.Math.*;

abstract class OptionImplementation {

    boolean asBoolean() throws ClassCastException {
        throw new ClassCastException("Option " + this + "cannot be interpreted as a boolean.");
    }

    int asInt() throws ClassCastException {
        throw new ClassCastException("Option " + this + "cannot be interpreted as an int.");
    }

    float asFloat() throws ClassCastException {
        throw new ClassCastException("Option " + this + "cannot be interpreted as a float.");
    }

    long asLong() throws ClassCastException {
        throw new ClassCastException("Option " + this + "cannot be interpreted as a long.");
    }

    double asDouble() throws ClassCastException {
        throw new ClassCastException("Option " + this + "cannot be interpreted as a double.");
    }

    Options asOptions() throws ClassCastException {
        throw new ClassCastException("Option " + this + "cannot be interpreted as multiple options.");
    }

    abstract void writeJson(JsonWriter writer) throws IOException;

    static class BooleanOption extends OptionImplementation {

        boolean value;

        public BooleanOption(boolean value) {
            this.value = value;
        }

        @Override
        boolean asBoolean() throws ClassCastException {
            return this.value;
        }

        @Override
        int asInt() throws ClassCastException {
            return this.value ? 1 : 0;
        }

        @Override
        float asFloat() throws ClassCastException {
            return this.value ? 1.0F : 0.0F;
        }

        @Override
        long asLong() throws ClassCastException {
            return this.value ? 1L : 0L;
        }

        @Override
        double asDouble() throws ClassCastException {
            return this.value ? 1.0 : 0.0;
        }

        @Override
        void writeJson(JsonWriter writer) throws IOException {
            writer.value(this.value);
        }

        @Override
        public String toString() {
            return Boolean.toString(this.value);
        }

    }

    static class NumberOption extends OptionImplementation {

        double value;

        public NumberOption(double value) {
            this.value = value;
        }

        @Override
        boolean asBoolean() throws ClassCastException {
            return this.value != 0.0;
        }

        @Override
        int asInt() throws ClassCastException {
            try {
                return toIntExact(round(this.value));
            } catch (ArithmeticException e) {
                throw new ClassCastException("Value " + this.value + " is to large to be interpreted as an int.");
            }
        }

        @Override
        float asFloat() throws ClassCastException {
            if (abs(this.value) > Float.MAX_VALUE) throw new ClassCastException("Value " + this.value + " does not fit in double range.");
            return (float)this.value;
        }

        @Override
        long asLong() throws ClassCastException {
            return round(this.value);
        }

        @Override
        double asDouble() {
            return this.value;
        }

        @Override
        void writeJson(JsonWriter writer) throws IOException {
            writer.value(this.value);
        }

        @Override
        public String toString() {
            return Double.toString(this.value);
        }

    }

    static class StringOption extends OptionImplementation {

        String value;

        public StringOption(String value) {
            this.value = value;
        }

        @Override
        boolean asBoolean() throws ClassCastException {
            return super.asBoolean();
        }

        @Override
        int asInt() throws ClassCastException {
            return super.asInt();
        }

        @Override
        float asFloat() throws ClassCastException {
            return super.asFloat();
        }

        @Override
        long asLong() throws ClassCastException {
            return super.asLong();
        }

        @Override
        double asDouble() throws ClassCastException {
            return super.asDouble();
        }

        @Override
        public String toString() {
            return this.value;
        }

        @Override
        void writeJson(JsonWriter writer) throws IOException {
            writer.value(this.value);
        }

    }


}
