;sec8-lab14
;Tianhao Liu
;liux3271

(defn fac [n]
    (apply * (map inc (map bigint (range n))))) ;for comparing result

(defn mul [lst]
    (apply * lst)
    )

(defn prl-factorial [n size]
    (apply * (map mul (partition-all size (map inc (map bigint (range n))))))
    )

(defn prl-sprt-by-line [text] ; for test.txt -> 36 ; for NASA's log -> 1891714
    (pmap (fn [lst] (apply str lst)) (partition-by (fn [x] (= x \newline)) text))
    )
