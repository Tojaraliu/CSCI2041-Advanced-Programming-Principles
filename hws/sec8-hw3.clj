;hw3
;Tianhao Liu
;03/07/2016

;step 1
(def p 6240322667N)
(def q 6240323147N)
(def n (* p q))
(def m (* (- p 1) (- q 1)))

(defn gcd [a b]
    (if (zero? b)
        a
        (gcd b (mod a b))
        )
    )

(defn finde [i m]
    (if (= i m)
        nil
        (if (and (not (nil? (modular-inverse i m))) (= 1 (gcd i m)))
            (cons i (lazy-seq (finde (inc i) m)))
            (lazy-seq (finde (inc i) m))
        )
    )
)

(defn find-e [m]
    (nth (take 100 (finde 2 m)) 99)
)

(def e (find-e m))

(def d (modular-inverse e m))

;step 2
(defn make-public-key [n e]
    {:mod n :exp e})

(defn make-private-key [n d]
    {:mod n :exp d})

(defn public-mod [pk]
    (get pk :mod)
    )

(defn public-exp [pk]
    (get pk :exp)
    )

(def public-key (make-public-key n e))

(def private-key (make-private-key n d))

;step 3
(defn string->ascii [s]
    (vec (map (fn [x] (- x 32)) (map int (map char s)))))

(defn ascii-num91 [v] (reduce (fn [x y] (+ (bigint (* 91 x)) y)) v))

(defn encrypt-word [word pk]
    (expmod
        (ascii-num91 (string->ascii word))
        (public-exp pk)
        (public-mod pk)
        )
    )

;step 4
(defn num->ascii [num]
    (if (zero? num)
        nil
        (cons (mod num 91) (num->ascii (quot num 91)))
        )
    )

(defn decrypt-word [ct pri-key]
    (let [lst (reverse (num->ascii (expmod ct (pri-key :exp) (pri-key :mod))))]
        (apply str (map char (map (fn [x] (+ x 32)) lst)))
        )
    )

;step 5
(defn partition-by-space [s]
    (map (fn [lst] (apply str lst)) (partition-by (fn [x] (= x \space)) s))
    )

;step 6
(defn encrypt-msg [s pk]
    (map (fn [word] (encrypt-word word pk)) (partition-by-space s))
    )

(defn decrypt-msg [lst pk]
    (reduce str
        (map
            (fn [word] (if (= word "") " " word))
            (map (fn [num] (decrypt-word num pk)) lst)
            )
        )
    )

;bonus
(defn encrypt-val [s pk]
    (encrypt-word s pk)
    )

(defn decrypt-val [n pk]
    (decrypt-word n pk)
    )
