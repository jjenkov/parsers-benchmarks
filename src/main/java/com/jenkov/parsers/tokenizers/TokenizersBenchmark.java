package com.jenkov.parsers.tokenizers;

import com.jenkov.parsers.unicode.Utf8Buffer;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by jjenkov on 07-11-2015.
 */
public class TokenizersBenchmark {


    @State(Scope.Thread)
    public static class TokenizerState {
        public String input = " + - \"this is a quoted token \" * &abc()def349.87iuy:899";
        public Utf8Buffer utf8Buffer = new Utf8Buffer(new byte[1024]);

        public BasicTokenizer basicTokenizer = new BasicTokenizer();
        public BasicTokenizerMethodized basicTokenizerMethodized = new BasicTokenizerMethodized();

        public TokenizerListenerIndexImpl tokenizerListenerIndex = new TokenizerListenerIndexImpl(1024);

        @Setup
        public void doSetup() {
            utf8Buffer.writeCodepoints(input);
        }

        @TearDown
        public void doTearDown() {
            utf8Buffer.clear();
        }
    }



    /*
    @State(Scope.Thread)
    public static class JacksonState {

        @Setup(Level.Invocation)
        public void doSetup() {

        }

    }
     */

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object basicTokenizerMethodized(TokenizerState state, Blackhole blackhole){
        state.basicTokenizerMethodized.tokenize(state.utf8Buffer, state.tokenizerListenerIndex);
        blackhole.consume(state.tokenizerListenerIndex);
        return state.tokenizerListenerIndex;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object basicTokenizer(TokenizerState state, Blackhole blackhole) {

        state.basicTokenizer.tokenize(state.utf8Buffer, state.tokenizerListenerIndex);
        blackhole.consume(state.tokenizerListenerIndex);
        return state.tokenizerListenerIndex;

        /*
        state.writer1_1.writeObject(state.pojo1_1, 1, state.dest1_1, 0);

        blackhole.consume(state.dest1_1);
        return state.dest1_1;
         */
    }

}
