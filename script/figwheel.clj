(require '[figwheel-sidecar.repl :as r]
         '[figwheel-sidecar.repl-api :as ra])

;; In Emacs use M-x cider-jack-in to start REPL and then C-c C-k) to
;; load this file and start figwheel.
;;
;; (Beware: piggieback must have been added to Leiningen dependencies, e.g.
;; as follows: [com.cemerick/piggieback "0.2.1"])
;;
;; See documentation at:
;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
;;
(ra/start-figwheel!
 {:figwheel-options {}
  :build-ids ["dev"]
  :all-builds
  [{:id "dev"
    :figwheel true
    :source-paths ["src"]
    :compiler {:main 'browser.core
               :asset-path "js"
               :output-to "resources/public/js/main.js"
               :output-dir "resources/public/js"
               :verbose true}}]})

(ra/cljs-repl)
