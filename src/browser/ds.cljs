(ns browser.ds
  (:require [datascript.core :as d]))

;; Database schema (only type ref entities need be specified).
(def schema {:app/dimensions {:db/valueType :db.type/ref}})

;; Database connection.
(def conn (d/create-conn schema))

;; Log database transactions for debug purposes. BEWARE: nil as a value
;; is not allowed and should not show up in logs!
(d/listen! conn :log 
           (fn [tx-report]
             (println (str "DS: " (:tx-data tx-report)))))

;; Initial contents of (in-memory) database.
(defn init!
  "Initializes database contents."
  []
  {:post [(not (nil?  %))]}
  (d/transact! conn
               [{:db/id -1
                 :navbar/key :navbar
                 :navbar/collapsed? true}

                {:db/id -2
                 :home/key :home
                 :home/title "HOME (to be done)"
                 :home/count 0}

                {:db/id -3
                 :about/key :about
                 :about/title "ABOUT (to be done)"}

                {:db/id -4
                 :about/key :error
                 :about/title "ERROR (to be done)"}

                {:db/id -100
                 :app/key :app
                 :app/page "/"
                 :app/locale :nl-NL
                 :app/logged-in? true
                 :app/dimensions {:db/id -1000
                                  :dimensions/orientation :landscape
                                  :dimensions/width 1024
                                  :dimensions/height 768}}]))

;;---------------------
;; Initialize database.
(init!)
