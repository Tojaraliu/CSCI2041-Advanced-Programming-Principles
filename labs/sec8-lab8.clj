;sec8-lab8
;Tianhao Liu
;liux3271

(defmacro avg [& lst]
    `(if (empty? '~lst)
        0
        (float (/ (apply + '~lst) (count '~lst)))
        )
    )

(defmacro avg-vect [lst]
    `(if (zero? (count ~lst))
        0
        (float (/ (apply + ~lst) (count ~lst)))
        )
    )

(defmacro numif [e p z n]
    `(cond
        (> ~e 0) ~p
        (= ~e 0) ~z
        (< ~e 0) ~n
        )
    )
; (defmacro numif-2 [e p z n]
;     (list 'cond (list '> e 0) p (list '= e 0) z (list) )))

(defmacro ntimes [n & body]
    `(loop [i# 0]
        (cond (< i# ~n)
            (do ~@body
                (recur (inc i#))
                )
            )
        )
    )

(defmacro ntimes2 [n & body]
    (loop [i n nb body]
        (if (= 1 i)
            `(do ~@nb)
            (recur (- i 1) (concat nb body))
            )
        )
    )

(defmacro my-dotimes [[i init end] & body]
    `(loop [~i ~init]
        (if (< ~i ~end)
            (do ~@body
                (recur (inc ~i)))
            nil
            )
        )
    )

(defmacro for-loop [[symb init test change] & body]
    `(loop [~symb ~init]
        (if (not ~test)
            nil
            (do ~@body
                (recur ~change)
                )
            )
        )
    )

(defn half-even [v]
    (let [n (count v) vv (atom v)]
        (loop [i 0]
            (if (= i n)
                @vv
                (do (if (even? (get @vv i))
                        (swap! vv #(assoc % i (/ (get % i) 2)))
                        )
                    (recur (inc i)))
                )
            )
        )
    )