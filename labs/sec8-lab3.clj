(defn remove-dupl [a b]
    (loop [op a source b]
        (if (empty? source)
            (into '() op)
            (if (.contains op (first source))
                (recur (remove (fn [x] (= (first source) x)) op) (rest source))
                (recur (conj op (first source)) (rest source))
                )
            )
        )
    )

(defn rd [a b]
    (filter (fn [x] (not (.contains b x))) a)
    )

(defn reduce [f coll]
    (loop [c coll]
        (if (= (count c) 1)
            (first c)
            (recur (conj (rest (rest c)) (f (first c) (first (rest c)) ) ) )
            )
        )
    )

(defn red [f coll]
    (if (= (count coll) 1)
        (first coll)
        (red f (cons (f (first coll) (second coll)) (drop 2 coll)))
        )
    )