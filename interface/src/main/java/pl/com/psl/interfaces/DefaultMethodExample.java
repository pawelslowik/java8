package pl.com.psl.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by psl on 31.05.17.
 */
public class DefaultMethodExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMethodExample.class);

    interface Readable {

        default void read() {
            LOGGER.info("Reading a {}", getType());
        }

        default String getType() {
            return "readable thing, not sure what though";
        }
    }

    interface Book extends Readable {

        default void read() {
            LOGGER.info("Reading a {} titled '{}'", getType(), getTitle());
        }

        @Override
        default String getType() {
            return "book";
        }

        default String getTitle() {
            return "<book title unknown>";
        }
    }

    interface Letter extends Readable {

        String getSubject();

        @Override
        default String getType() {
            return "letter";
        }
    }

    interface Song extends Readable {

        String getTitle();

        @Override
        default String getType() {
            return "song";
        }
    }

    private static class FamousBlueRaincoat implements Song, Letter {

        private static final String TITLE  = "Famous Blue Raincoat";

        @Override
        public String getSubject() {
            return TITLE;
        }

        @Override
        public String getTitle() {
            return TITLE;
        }

        @Override
        public String getType() {
            //has to be implemented, otherwise there's a conflict!
            return "song/poem";
        }
    }

    private static abstract class Epistolary implements Book, Letter {

        @Override
        public String getType() {
            return "epistolary";
        }

        @Override
        public void read() {
            LOGGER.info("Reading an epistolary titled '{}'", getTitle());
        }
    }

    private static class TheSorrowsOfYoungWerther extends Epistolary implements Book {

        private static final String TITLE = "The Sorrows of Young Werther";

        @Override
        public String getTitle() {
            return TITLE;
        }

        @Override
        public String getSubject() {
            return TITLE;
        }

        //getType() doesn't have to be implemented, there's no conflict, superclass always wins with interface
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

        Book dune = new Book() {
            @Override
            public String getTitle() {
                return "Dune";
            }
        };

        Letter loveLetter = () -> "A love letter";

        Song stairwayToHeaven = new Song() {
            @Override
            public String getTitle() {
                return "Stairway to Heaven";
            }

            @Override
            public void read() {
                LOGGER.info("Reading a {} titled '{}'", getType(), getTitle());
            }
        };

        FamousBlueRaincoat famousBlueRaincoat = new FamousBlueRaincoat();
        TheSorrowsOfYoungWerther theSorrowsOfYoungWerther = new TheSorrowsOfYoungWerther();

        mobyDick.read();
        dune.read();
        loveLetter.read();
        stairwayToHeaven.read();
        famousBlueRaincoat.read();
        theSorrowsOfYoungWerther.read();
    }
}
