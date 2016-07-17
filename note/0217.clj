;0217

Exam
(defn first-even+greater [n lst]
    (first (filter (fn [x] (and (even? x) (> x n))) lst))

(defn not-every? [pred lst]
    (= (count (filter (fn [x] (not (pred x))) lst)) (count lst))
    )