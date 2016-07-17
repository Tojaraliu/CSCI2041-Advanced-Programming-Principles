;sec8-lab9
;Tianhao Liu
;liux3271

(defn same-elements? [lst1 lst2]
    (if (not= (count lst1) (count lst2))
        false
        (= (set lst1) (set lst2))
        )
    )

(defn sum-harmonic [n]
    (if (zero? n)
        0
        (+ (/ n) (sum-harmonic (dec n)))
        )
    )

(defn collect [lst n]
    (if (empty? lst)
        nil
        (cons (take n lst) (collect (drop n lst) n))
        )
    )

(defn interleave [lst1 lst2]
    (if (empty? lst1)
        lst2
        (if (empty? lst2)
            lst1
            (cons (first lst1) (cons (first lst2) (interleave (rest lst1) (rest lst2))))
            )
        )
    )

(let [secret (atom "mistery")]
    (defn read-secret [] @secret)
    (defn multiply-secret [n] (swap! secret (fn [s] (repeat n s)) ) @secret)
    )
