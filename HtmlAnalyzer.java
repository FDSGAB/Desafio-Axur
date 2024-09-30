import java.util.Stack;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class HtmlAnalyzer {

    public static class DeeperSentence {
        String sentence = new String();
        int depth = -1;

        public void updateSentence(String sentence, int depth) {
            if (this.depth < depth) {
                this.sentence = sentence;
                this.depth = depth;
            }
        }
    }

    public static class TagReader {

        String status = "unidentified";
        StringBuilder tagName = new StringBuilder();

        private void evaluateStatusBy(char character) {
            if (character == '<') {
                this.status = "identified";
            } else if (character == '>') {
                this.status = "finished";
            }
        }

        public void process(char character) {
            this.evaluateStatusBy(character);
            if (this.status.equals("identified") && character != '<') {
                tagName.append(character);
            }
        }

        public void reset() {
            this.tagName.delete(0, this.tagName.length());
            this.status = "unidentified";
        }

        public boolean isClosingTag() {
            if (this.status.equals("finished")) {
                return tagName.charAt(0) == '/' ? true : false;
            }
            return false;
        }

        public String tagName() {
            if (tagName.charAt(0) == '/') {
                tagName.setCharAt(0, ' ');
                String result = tagName.toString().trim();
                tagName.setCharAt(0, '/');
                return result;
            }
            return tagName.toString();
        }

        public boolean isFinished(){
            return this.status.equals("finished");
        }

        public boolean isUnidentified(){
            return this.status.equals("unidentified");
        }


    }

    public static void main(String[] args) {
        String url = args[0];
        System.out.println(findDeeperInnerSentence(url));
    }

    public static String findDeeperInnerSentence(String url) {
        DeeperSentence deeperSentence = new DeeperSentence();
        Stack<String> stack = new Stack<>();
        StringBuilder sentenceBuilder = new StringBuilder(120);
        TagReader tagReader = new TagReader();
        int symbol = -1;
        try (InputStream inputStream = new URL(url).openStream()) {
            while ((symbol = inputStream.read()) != -1) {
                char symbolCharacter = (char) symbol;
                tagReader.process(symbolCharacter);
                if (tagReader.isFinished()) {
                    if (tagReader.isClosingTag()) {
                        if (stack.isEmpty() || !stack.peek().equals(tagReader.tagName())) {
                            return "malformed HTML";
                        }
                        deeperSentence.updateSentence(sentenceBuilder.toString().trim(), stack.size());
                        sentenceBuilder.delete(0, sentenceBuilder.length());
                        stack.pop();
                        tagReader.reset();
                    } else {
                        stack.push(tagReader.tagName.toString());
                        tagReader.reset();
                    }
                } else if (tagReader.isUnidentified()) {
                    sentenceBuilder.append(symbolCharacter);
                }
            }
        } catch (IOException ex) {
            return "URL connection error";
        }
        return stack.isEmpty() ? deeperSentence.sentence : "malformed HTML";
    }
}
