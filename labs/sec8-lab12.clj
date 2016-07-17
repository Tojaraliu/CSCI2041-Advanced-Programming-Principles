;sec8-lab12
;Tianhao Liu
;liux3271

(use 'clojure.java.io)

(defn readAndPrint [filename]
    (with-open [rdr (reader filename)]
        (doseq [line (line-seq rdr)]
            (prn line)
            )
        )
    )

(defn detectIP  [filename]
    (with-open [rdr (reader filename)]
        (doseq [line (line-seq rdr)]
            (let [ip (first (re-seq #"\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}" line))]
                (if (nil? ip)
                    (prn (first (re-seq #"[^ ]+" line)))
                    (prn ip)
                    )
                )
            )
        )
    )

(defn detectnum  [filename]
    (with-open [rdr (reader filename)]
        (doseq [line (line-seq rdr)]
            (prn (last (re-seq #"\d{1,6}" line)))
            )
        )
    )
