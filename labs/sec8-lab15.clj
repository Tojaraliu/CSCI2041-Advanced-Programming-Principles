;sec8-lab15
;Tianhao Liu
;liux3271

(defmacro time2
    "Evaluates expr and prints the time it took. Returns the value of expr."
    [expr]
    `(let [start# (. System (nanoTime)) ret# ~expr]
        [
            (/ (double (- (. System (nanoTime)) start#)) 1000000.0)
            ret#
            ]
        )
    )

(defmacro definefcn [f arglist & body]
    `(def ~f (fn ~arglist ~@body))
    )

(defn reduce-by [v key1 key2]
    (loop [nv v ret {}]
        (if (empty? nv)
            ret
            (recur (rest nv)
                (if (contains? ret (key1 (first nv)))
                    (update-in ret [(key1 (first nv))] #(+ % (key2 (first nv))))
                    (assoc ret (key1 (first nv)) (key2 (first nv)))
                    )
                )
            )
        )
    )

(defn fac-using-atoms-while [n]
    (let [i (atom 0) result (atom 1)]
        (while (> n @i)
            (swap! i inc) (swap! result * @i)
            )
        @result
        )
    )

(defn sqr-n [x n]
    (loop [ret x i n]
        (if (zero? i)
            ret
            (recur (* ret ret) (dec i))
            )
        )
    )

(defn double-el [v nofth nofi]
    (let [parts
            (if (= (mod (count v) nofth) 0)
                (partition-all (quot (count v) nofth) v)
                (partition-all (int (Math/ceil (/ (count v) nofth))) v))]
        (vec (apply concat (pmap (fn [lst] (map #(sqr-n % nofi) lst)) parts)))
        )
    )
