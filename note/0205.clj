;0205
;MAP

{:a 3 :2 25}
(hash-map :a 3 :z 25) = {:a 3 :z 25}
(sorted-map )
(zipmap [keys] [values])
(zipmap [:a :z] [3 25])
(get {:a 2 :z 3} :z)
({:a 2 :z 3} :z)

(find {:a 2 :z 3} :a) -> (:a 2)
(get {:a 2 :z 3} :a) -> 2
(keys {:a 2 :z 3} :a) -> [:a :z]
(vals {:a 2 :z 3} :a) -> [2 3]
(merge {:a 2} {:b 3})
(assoc {:a 2 :z 3} :b 10)
(into)