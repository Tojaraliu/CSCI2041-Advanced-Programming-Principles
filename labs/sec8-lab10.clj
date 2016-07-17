(defmacro car [lst]
    `(first ~lst))

(defmacro my-if
    ([condition then x else y]
    (if (and (not= then 'then) (not= else 'else))
        "error"
        `(if ~condition
            ~x
            ~y
            )
        ))
    ([condition then x]
    (if (not= then 'then)
        "error"
        `(if ~condition
            ~x
            )
        )
    )
    )

(defn lazy-interleave [lst1 lst2]
    (if (and (empty? lst1) (empty? lst2))
        nil
        (cons (first lst1) (cons (first lst2) (lazy-seq (lazy-interleave (rest lst1) (rest lst2)))))
        )
    )

(defn lazy-random [n]
    (cons (rand-int n) (lazy-seq (lazy-random n)))
    )

;Problem 5
;1)
(let [num (atom 1) val @num]
    (swap! num inc)
    (println "previous: " val)
    @num)

;2)
(def ag (agent {}))
(defn action [m key]
    (assoc m key 10))
(send ag action :b)
@ag

;3)
(def ^:dynamic x)
(def ^:dynamic y)
(binding [x 2 y 3]
    (set! x (+ x y)))
