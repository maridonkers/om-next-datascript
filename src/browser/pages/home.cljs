(ns browser.pages.home
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [datascript.core :as d]

            [browser.util :as util]

            [browser.parsers.home :as home-parser]))

;; ------------------------------------
;; Om Next component for the home page.
(defui HomePage
  static om/IQuery
  (query [this]
         [:db/id :home/title :home/count])
  Object
  (render [this]
          (let [props (om/props this)

                {:keys [db/id
                        home/title
                        home/count]} props]
            
            (dom/div
             nil 
             (dom/h2 nil title)
             (dom/span nil (str "Home (count): " count))
             (dom/button
              #js {:type "button"
                   :onClick (fn [e]
                              (util/nav! "/about")
                              (let [entity {:db/id id :home/count count}]
                                (om/transact! this
                                              `[(home/increment ~entity)])))}
              "Increment!")))))

(def home-page (om/factory HomePage))
