(ns minichess.core
  (:require [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response   :refer [response]]
            [ring.adapter.jetty   :refer [run-jetty]]))



(defn populate-board [board]
  ;"Adiciona pecas a um board vazio"
  {:peao   [:A2 :B2 :C2 :D2 :E2 :F2 :G2 :H2, :A7 :B7 :C7 :D7 :E7 :F7 :G7 :H7]
   :torre  [:A1 :H1 , :A8 :H8]
   :cavalo [:B1 :G1, :B8 :G8]
   :bispo  [:C1 :F1, :C8 :F8]
   :rei    [:E1, :E8]
   :rainha [:D1, :D8]})

(defn board []
     ;"Cria um board vazio - hash-map"
      (into {} (map (fn [x] (into (hash-map (keyword (first x)) (second x)))))
          (partition 2
            (flatten (map (fn [letter]
                        (let [dict {}]
                          (for [x (range 1 9)]
                          (seq (assoc dict (str letter x) {})))))
                          ["A" "B" "C" "D" "E" "F" "G" "H"])))))

;;         I      T
;; peao    8  --  16  {:peao {:cor "preto"}}
;; torre   2  --  4
;; cavalo  2  --  4
;; bispo   2  --  4
;; rei     1  --  2
;; rainha  1  --  2

;; A1 > C3



(defn handler [request]
  ;"Handle the client request"
  (let [url (:uri request)]
    (if (= url "/board")
      (response {:board (board)})
      (response "BLA BLA BLA"))))

(def app
  ;Middleware functions
  (wrap-json-response handler))

(defn -main []
  ;"Funcao que é executada com o comando lein run"
  (run-jetty app {:port 3000}))
