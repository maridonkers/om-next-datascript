(ns browser.navbar
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [browser.parsers.navbar :as navbar-parser]))

;;-------------------
;; Om Next component.
(defui Navbar
  static om/IQuery
  (query [this]
         [:db/id
          :navbar/collapsed?])
  Object
  (render
   [this]
   (dom/div
    nil
    (let [props (om/props this)
          
          {:keys [navbar/collapsed?]} props

          cmp (om/get-computed props)
          {:keys [app-id
                  lc
                  logged-in?]} cmp]

      (when logged-in?
        (dom/ul
         nil
         #js {:href "#/"
              :onClick
              #(let [entity {:db/id app-id}]
                 (om/transact! this
                               `[(app/logout ~entity)]))} Logout))))))

(def navbar (om/factory Navbar))
