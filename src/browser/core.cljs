(ns browser.core
  (:require [browser.util :as util]
            [browser.routing :as routing]))

(enable-console-print!)

;; -------------------------
;; Set-up.
(routing/hook-browser-navigation!)
(routing/om-next-root!)
(routing/restore-page!)
