;cheat sheet
(defn foo [a b]
    ...
    )
{:pre [(not (= a 0))]}

Anonymous Function
(fn [n] (+ 3 x))
(map (fn [n] (+ 3 x)) [1 2 3]) -> (4 5 6)
(map inc [1 2 3])
(map (fn [x y] (+ x y)) [1 2] [3 4]) -> (4 6) //1+3 and 2+4

Multi-arity functions
(defn range
     ([a]
          …)
     ([a b]
          …)
     ([a b c]
          …))

Variadic Function
(defn foo1 [a & args]
     ……)

Named parameters
(defn foo2
     [& {:keys [p1 p2] :or {p1 [0] p2 [1]}}] // 0 and 1 are default values
(foo2 :p1 7 :p2 10)
(foo2 :p2 10 :p1 7)
(foo2 :p2 15)

(filter even? [1 2 3 4 5 6]) -> (2 4 6)
(reduce + [1 2 3]) -> 6
(apply + [1 2 3])

(let [b 4]
     (println b)
     (let [a 100]
          (+ a b))
     (* 2 b)
)

(defn sumdowneven [n rs]
     (if (= n 0)
          rs
          (if (even? n)
               (recur (dec n) (+ rs n)) ;tail recursive
               (recur (dec n) rs))))

function that returns a function
(defn divisible [m]
    (fn [num]
        (= 0 (mod num m))
        )
    )
((divisible 3) 6)
(def divby3 (divisible 3))
(def divby4 (divisible 4))
(divby3 10) -> false

(def divby3
    #(= 0 (mod %1 3))
    )

(def makelist2
    #(list %1 %2)
    )

(rseq [1 2]) -> (2 1)
(seq [1 2]) -> (1 2)
(vec '(1 2)) -> [1 2]
(reverse [] ())

{:a 3 :b 25}
(hash-map :a 3 :z 25) = {:a 3 :z 25}
(sorted-map )
(zipmap [keys] [values])
(zipmap [:a :z] [3 25])
(get {:a 2 :z 3} :z)
({:a 2 :z 3} :z)

(find {:a 2 :z 3} :a) -> [:a 2]
(get {:a 2 :z 3} :a) -> 2
(keys {:a 2 :z 3} :a) -> [:a :z]
(vals {:a 2 :z 3} :a) -> [2 3]
(merge {:a 2} {:b 3})
(assoc {:a 2 :z 3} :b 10)
(into)
(last [1 2 3 4 5]) -> 5
(repeat 4 6) -> (6 6 6 6 6)

(def fourth (comp first rest rest rest))
user=> (fourth [1 2 3 4 5]) -> 4

(identical? 'snow 'snow) -> false
(let [x 'snow y x]
    (identical? x y)) -> true

user=> (hash-map :a 3. :z 25)
{:z 25, :a 3.0}
user=> (sorted-map :a 3. :z 25 :c 14)
{:a 3.0, :c 14, :z 25}
user=> (array-map :h 3 :z 25 :c 14)
{:h 3.0, :z 25, :c 14}
Keys do not need to be keywords:
user=> (hash-map [1 2 3] 40 :b "hello")
{[1 2 3] 40, :b "hello"}
If we have a vector of keys and one of values we can construct a map using
user=> (zipmap [:a :z :c] [ 3 25 14])
{:c 14, :z 25, :a 3}
(find {:a 3, :z 25, :c 14} :z) -> [:z 25]
(assoc {:a 3.0, :z 25, :c 14} :k 99) -> {:k 99, :z 25, :a 3.0, :c 14}
(assoc {:a 3.0, :z 25, :c 14} :z 99) -> {:z 99, :a 3.0, :c 14}
(seq {:a 4 :b 6}) -> ([:a 4] [:b 6])

(defn [& args]
    (println args)) -> list

(sequential? [1 2 3 4]) => true
(sequential? (seq [1 2 3 4])) => true
(sequential? {1 2 3 4}) => false
(seq s) -> (\t \h \i \s \space \i \s \space \a \space \t \e \s \t)
(int \t) -> 116
(int \T) -> 84
(int \space) -> 32
(char 32) -> \space
l = (\t \h \i \s \space \i \s \space \a \space \t \e \s \t)
(map int l) -> (116 104 105 115 32 105 115 32 97 32 116 101 115 116)
(apply str (map char (map int l))) -> original string
(map str (map char (map int (seq s)))) ->
    ("t" "h" "i" "s" " " "i" "s" " " "a" " " "t" "e" "s" "t")

(defn duplicate [n]
    (map (fn [x] (repeat 2 x)) n)
    )

(defn replicate[v time]
    (map (fn [x] (repeat time x)) v)
    )

(defn below? [f th low high]
    (= (filter (fn [x] (> th (f x))) (range low (+ 1 high))) (range low (+ 1 high)))
    )
