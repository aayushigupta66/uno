package deck;

/**
 * Interface that keeps track of what differentiates between different cards.
 */
public interface CardInfo {

    /**
     * Type enum that keeps track of the card's type.
     */
    public enum Type {
        WILD {
            public String toString() { return "wild"; }
        },
        WILD_DRAW_4 {
            public String toString() { return "wild_4"; }
        },
        NORMAL {
            public String toString() {
                return "normal";
            }
        },
        SKIP {
            public String toString() {
                return "skip";
            }
        },
        REVERSE {
            public String toString() {
                return "reverse";
            }
        },
        DRAW_2 {
            public String toString() {
                return "plus2";
            }
        }
    }

    /**
     * Color enum that keeps track of the card's color.
     */
    public enum Color {
        RED {
            public String toString() {
                return "red";
            }
        },
        YELLOW {
            public String toString() {
                return "yellow";
            }
        },
        GREEN {
            public String toString() {
                return "green";
            }
        },
        BLUE {
            public String toString() {
                return "blue";
            }
        },
        NONE {
            public String toString() {
                return "";
            }
        }
    }

}