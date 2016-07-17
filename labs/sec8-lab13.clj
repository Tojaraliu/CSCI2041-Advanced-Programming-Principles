;sec8-lab13
;Tianhao Liu
;liux3271

(ns my.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 60)
  (q/color-mode :hsb)
  {:color 0
   :angle 0})

(defn update-state [state]
  (let [{:keys [color angle]} state]
    {:color (mod (+ color 0.7) 255)
     :angle (mod (+ angle 0.05) q/TWO-PI)}))

(defn draw-state [state]
  (q/background 240)
  (q/fill (:color state) 255 255)
  (let [angle (:angle state)
        x (* 200 (q/cos angle))
        y (* 120 (q/sin angle) (q/cos angle))]
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      (q/ellipse x y 100 100)
      (q/ellipse y x 50 50))
    )
)

(defn clickfunc [state event]
  (assoc state :color (rand-int 255))
  )

(q/defsketch my
  :host "host"
  :size [1000 1000]
  :setup setup
  :update update-state
  :draw draw-state
  :mouse-clicked clickfunc
  :middleware [m/fun-mode])