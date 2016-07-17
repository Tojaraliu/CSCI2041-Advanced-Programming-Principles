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