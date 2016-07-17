;0210

(defn incr "III" [k] ;doc string
    (defn xxx "XXXXX" [n] (+ n k))
    xxx
    )

(defn divby [n k]
    (= 0 (mod n k))
    )

(map divby '(5 6) '(2 3)) -> (false true)