;sec8-hw7
;Tianhao Liu
;liux3271@umn.edu
;Share URL: http://quil.info/sketches/show/-KGYYi3_GnRmY-lh9Yay
;Introduction:
;     In this game, you will have one short plate on each side of the
;     sketch, which you will use to bounce back the balls. If any
;     ball reaches the edge and no plates can bounce it back, you
;     will lose that ball.
;     Two vertical plates will always move simultaneously and so will
;     two horizontal plates. To move two plates on left and right
;     sides, use up and down arrow and to move two plates on up
;     and down sides, use left and right arrow. At the very
;     begining, there are 25 balls, and you need to keep at least
;     8 balls to survive. Try to see how long you can hold it!
;     Press P to pause the game.
;     Right-click the sketch to change the background grayness
;==========================================================
(ns circles.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

;;; Circles Game Basic Setup
;;; CSCI 2041 Homework #7
;;; Spring 2016

;;; Constants
(def speed 3)                          ;maximm speed circles move
(def numofballs 25)

;---------------------------------------------------------------------
; Setup
;---------------------------------------------------------------------

(defn make-circle
  "Creates a circle with a random color and set speed and heading."
   [x y]
  (let [angle (rand q/TWO-PI)          ;random angle
        cur-speed (+ (rand speed) 3)]  ;random speed up to our constant
       {:x x                           ;set this circle's x
    	  :y y                           ;set this circle's y
        :size (+ 10 (rand 15))         ;set random diameter
    	:color (rand 255)              ;make this colorful
    	:speed cur-speed               ;set this circle's speed
      :gone? false
    	:heading angle}                ;set this circle's heading
    ))                                 ;returns circle

(defn setup
  "Set up a sketch and return initial state."
  []
  (q/frame-rate 30)                    ;frequency update and draw functions
  (q/color-mode :hsb)                  ;how we represent colors
  (let [size (q/width)
        n numofballs
        bg 250]
       (q/background bg)               ;nice light grey color for the bg
       ;; need to make n circles of random sizes
       ;; here we make only one circle in a list
       {:circles
          (loop [cs '() cnt numofballs]
            (if (zero? cnt)
              cs
              (recur
                (conj cs (make-circle (+ 200 (rand-int 100)) (+ 200 (rand-int 100))))
                (dec cnt))
              )
            )
        :plates {:x_offset 125 :y_offset 125 :len 250}
        :fail? false
        :gametime 0
        :running? true                 ;so we can pause and unpause in update
        :n n                           ;how many circles
        :size size                     ;how big is the sketch
        :bg bg                         ;we might want to change this later
        }))

;---------------------------------------------------------------------
; Update functions
;---------------------------------------------------------------------

(defn quadrant [angle]
    (quot angle q/HALF_PI)
  )

(defn wall-hit [c size plates]
  (let [x (:x c) y (:y c)
        xleft (:x_offset plates) xright (+ (:x_offset plates) (:len plates))
        yup (:y_offset plates) ydown (+ (:y_offset plates) (:len plates))]
    (cond
      (> x (- size (:size c)))
          (if (and (>= y yup) (<= y ydown))
            0 -2
            )
      (< y (:size c))
          (if (and (>= x xleft) (<= x xright))
            1 -2
            )
      (< x (:size c))
          (if (and (>= y yup) (<= y ydown))
            2 -2
            )
      (> y (- size (:size c)))
          (if (and (>= x xleft) (<= x xright))
            3 -2
            )
      :else -1
      )
    )
  )

(defn bounce-back [c size plates]
  (let [quad (quadrant (:heading c)) wall (wall-hit c size plates) head (:heading c)]
    (if (= -1 wall)
      c ; not hit any wall
      (cond
        (= -2 wall) (assoc c :gone? true)
        (and (= 0 quad) (= 0 wall)) (assoc c :heading (- q/PI head))
        (and (= 0 quad) (= 1 wall)) (assoc c :heading (- q/TWO_PI head))

        (and (= 1 quad) (= 1 wall)) (assoc c :heading (- q/TWO_PI head))
        (and (= 1 quad) (= 2 wall)) (assoc c :heading (- q/PI head))

        (and (= 2 quad) (= 2 wall)) (assoc c :heading (- (* q/PI 3) head))
        (and (= 2 quad) (= 3 wall)) (assoc c :heading (- q/TWO_PI head))

        (and (= 3 quad) (= 3 wall)) (assoc c :heading (- q/TWO_PI head))
        (and (= 3 quad) (= 0 wall)) (assoc c :heading (- (* q/PI 3) head))
        :else c
        )
      )
    )
  )

(defn move-circle
  "Moves a circle according to its speed and heading"
  [c state]
  (let [x (:x c) y (:y c) angle (:heading c) speed (:speed c)]
    (bounce-back
      (assoc c :x (+ x (* (q/cos angle) speed))
               :y (- y (* (q/sin angle) speed)))
      (:size state)
      (:plates state)
      )
    )
  )

(defn update-circles
  "Moves each circle and returns updated vector of circles."
  [circles state]
  (filter #(not (:gone? %)) (map (fn [c] (move-circle c state)) circles)))

(defn update-state
  "Updates sketch state. If it is paused, then the state is returned unmodified."
  [state]
  (if (:running? state)
      ;add some movement and update functions so the next line moves circles
      (let [newstate (assoc state :circles (update-circles (:circles state) state))]
        (if (< (count (newstate :circles)) 8)
          (assoc newstate :fail? true)
          (update-in newstate [:gametime] q/millis)
          )
        )
      state
    )
  )

;---------------------------------------------------------------------
; Draw functions
;---------------------------------------------------------------------

(defn draw_plates [plates size]
  (let [xos (:x_offset plates) yos (:y_offset plates) len (:len plates)]
    (q/fill 105 105 105) ;dimgray
    (q/rect xos 0 len 3)
    (q/rect xos (- size 6) len 3)
    (q/rect 0 yos 3 len)
    (q/rect (- size 6) yos 3 len)
    )
  )

(defn draw-circle
  "Draws an individual circle with correct color, location, and size."
  [c]
  (q/fill (:color c) 255 255)
  (q/ellipse (:x c) (:y c) (:size c) (:size c))
  )

(defn draw-state
  "Draws the sketch state."
  [state]
  (q/background (:bg state))                    ;update the background
  (q/stroke 1)                                  ;how wide should the lines be
  (dorun (map draw-circle (:circles state)))    ;map is lazy
  (draw_plates (:plates state) (:size state))
  (q/fill 0)
  (q/text (str "Balls left: " (count (:circles state))) 20 20)
  (q/text (str "Time: " (/ (:gametime state) 1000) "s") 100 20)
  (if (not (:running? state))
    (q/text "Paused" 420 20)
    )
  (if (:fail? state)
    (q/text "Game Over!" 230 230)
    )
  )

;---------------------------------------------------------------------
; User interaction functions
;---------------------------------------------------------------------

(defn mouse-clicked
  "Changes background color to different shades of grey."
  [state event]
  (update-in state [:bg] (fn [n] (+ 200 (rand-int 50))))) ; Avoid making the background too dark to see the plates

(defn key-pressed
  "Process key event. p will pause/unpause everything."
  [state event]
  (condp = (:key event)
    :p (assoc (update-in state [:running?] not) :first_pause false)
    :up (assoc state :plates
      (update-in (:plates state) [:y_offset] #(max 0 (- % 30))))
    :down (assoc state :plates
      (update-in (:plates state) [:y_offset] #(min (- (:size state) (:len (:plates state))) (+ 30 %))))
    :left (assoc state :plates
      (update-in (:plates state) [:x_offset] #(max 0 (- % 30))))
    :right (assoc state :plates
      (update-in (:plates state) [:x_offset] #(min (- (:size state) (:len (:plates state))) (+ 30 %))))
    state))

(q/defsketch circles
    :host "host"
    :size [500 500]                ;we need a square canvas
    :setup setup                   ;getting things started, setting initial state
    :update update-state           ;the function to update the state
    :draw draw-state               ;the necessary draw function
    :mouse-clicked mouse-clicked   ;this is our mouse click event
    :key-pressed key-pressed       ;this is our keyboard input event
    :middleware [m/fun-mode])      ;this gives us the ability to have state
