(ns browser.routing
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [datascript.core :as d]
            
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]

            [browser.ds :as ds]
            [browser.util :as util]
            [browser.reconciler :refer [reconciler parser]]
            
            [browser.app :refer [App]])
  (:import goog.History))

;;-------------
;; Change page.
;;
;; Use Om Next parser to avoid direct knowledge of underlying storage.
;;
(defn set-page!
  "Sets page in Om Next data."
  [new-page]

  (let [app-props (parser {:state ds/conn}
                          [{:app/query [:db/id :app/page]}])
        entity (get-in app-props [:app/query 0])
        {:keys [db/id
                app/page]} entity]

    (when (not= page new-page)
      (parser {:state ds/conn}
              `[(app/set-page ~{:db/id id :app/page new-page})]))))

;; -------
;; Routes.
;; Extend when pages added. Also see case-statement
;; in browser.app component.
;;
(secretary/set-config! :prefix "#")

(secretary/defroute home-page "/" []
  (set-page! "/"))  

(secretary/defroute about-page "/about" []
  (set-page! "/about"))

;; --------
;; History.
;; must be called after routes have been defined.
(defn hook-browser-navigation! 
  "Connects browser navigation to secretary routing."
  []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn om-next-root!
  "Sets Om Next root component."
  []
  (om/add-root! reconciler
                App (gdom/getElement "app")))

(defn restore-page!
  "Restores saved page (if any); otherwise home page."
  []

  (if-let [url (d/q '[:find ?p .
                      :where [?e :app/page ?p]] @ds/conn)]
    (util/nav! url)))
