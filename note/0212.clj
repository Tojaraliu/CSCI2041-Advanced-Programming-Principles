;0212

(def a "abcde")
(seq a) -> (\a \b \c \d \e)
(int \t) -> 84
(char 40) -> \(
(char 41) -> \)

(def ints (map int "test")) -> (116 101 115 116)
(apply str (map char ints)) -> "test"