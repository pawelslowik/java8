package pl.com.psl.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by psl on 31.05.17.
 */
public class StaticMethodExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticMethodExample.class);

    interface Writable {

        static void write(Writable writable) {
            LOGGER.info("Writing a {}", writable.getType());
        }

        String getType();
    }

    interface Book extends Writable {

        static void write(Book book) {
            LOGGER.info("Writing a {} titled '{}'", book.getType(), book.getTitle());
        }

        String getTitle();
    }

    interface Letter extends Writable {

        static void write(Writable writable) {
            LOGGER.info("Writing a " + writable.getType());
        }

        String getSubject();
    }

    interface Song extends Writable {
        String getTitle();
    }

    public static void main(String[] args) {
        Book mobyDick = new Book() {
            @Override
            public String getTitle() {
                return "Moby Dick";
            }

            @Override
            public String getType() {
                return "novel";
            }
        };

        Letter loveLetter = new Letter() {
            @Override
            public String getSubject() {
                return "A love letter";
            }

            @Override
            public String getType() {
                return "letter";
            }
        };

        Song stairwayToHeaven = new Song() {
            @Override
            public String getTitle() {
                return "Stairway to Heaven";
            }

            @Override
            public String getType() {
                return "rock song";
            }
        };

        Book.write(mobyDick);
        Letter.write(loveLetter);
        Letter.write(stairwayToHeaven);
        Writable.write(stairwayToHeaven);
        //does not compile, Song does not define a write method and superinterface write method can't be called via Song
        //Song.write(stairwayToHeaven);

        //does not compile, can't call a static method defined in an interface on that interface instance
        //mobyDick.write(mobyDick);
    }
}
