(ns house.rooms.back
  (:require [advenjure.rooms :as room]
            [advenjure.items :as item]
            [advenjure.utils :as utils]
            [house.puzzles.bath-window :refer [piano-stool]]
            [house.puzzles.catalog :refer [catalog]]
            [house.puzzles.safe :refer [safe-portrait]]
            [house.puzzles.oak-door :refer [oak-door-back]]
            [house.puzzles.gate-door :refer [key-set]]
            [house.puzzles.candles :refer [candles candlestick]]))

; common corridor items
(def corridor (item/make ["corridor" "passage" "passageway" "hall" "hallway"]
                         "The corridor had a high roof and a squeaky wooden floor, covered by a rug. There were some paintings between the doors."))

(def paintings (item/make ["paintings" "painting" "portraits" "portraits"]
                          "I didn't know much about arts, but those looked expensive."
                          :take "They were good on the wall."))

(def floor (item/make "floor" "Old, wooden, noisy. Very much like the rest of the house."))

(def rug (item/make "rug" "An ornate long rug."))

(defn make-door [extra-names goes-to]
   (item/make (apply conj ["door"] extra-names)
      "A wooden door."
      :closed true
      :enter goes-to))

(def back-hall3 (->
                  (room/make "Corridor"
                             "The end of a corridor with rooms to the east and west, and the door to the back garden"
                             :initial-description "My steps resounded throughout a long corridor, with a series of rooms on each side. On the other end was an oak door, ajar."
                             :points 100)
                  (room/add-item (make-door ["west door" "w door"] :bedroom2) "")
                  (room/add-item (make-door ["east door" "e door"] :bedroom3) "")
                  (room/add-item corridor "")
                  (room/add-item paintings "")
                  (room/add-item floor "")
                  (room/add-item rug "")))

(def fake-door (item/make ["door" "west door" "w door"]
                          "It was the same as the other doors in the corridor, except it didn't have a doorknob."
                          :open "I couldn't open the west door, it didn't have a doorknob."
                          :id "no-knob-door"))

(def back-hall2 (->
                  (room/make "Corridor"
                             "The middle section of a corridor. There were doors to the east and west, but the latter didn't have a doorknob."
                             :initial-description "The rug that traversed the room wasn't enough to silence the creak of the floor, as if the house denounced my irruption."
                             :known true)
                  (room/add-item fake-door "")
                  (room/add-item (make-door ["east door" "e door"] :tapestry) "")
                  (room/add-item corridor "")
                  (room/add-item paintings "")
                  (room/add-item floor "")
                  (room/add-item rug "")))

(def back-hall1 (->
                  (room/make "Corridor"
                             "The massive oak door was blocking my way to the front section of the house. There were rooms to the east and west."
                             :initial-description "The massive oak door separated the corridor from the front section of the house; it was slightly ajar. As I reached for the knob, the door was suddenly pulled back and slammed shut; sounds of a key and a bolt followed immediately.\n \nThey heard me coming and they locked me out."
                             :known true)
                  (room/add-item corridor "")
                  (room/add-item paintings "")
                  (room/add-item floor "")
                  (room/add-item rug "")
                  (room/add-item (make-door ["west door" "w door"] :library) "")
                  (room/add-item (make-door ["east door" "e door"] :dining) "")
                  (room/add-item oak-door-back "")))

(def top-drawer (item/make ["drawer" "top drawer"]
                           "There was nothing special about it."
                           :open "Tablecloths, placemats and napkins."
                           :look-in "Tablecloths, placemats and napkins."))

(defn post-open
  [old gs]
  (let [drawer (utils/find-first gs "bottom drawer")
        new-drawer (update-in drawer [:open] dissoc :say)]
    (utils/replace-item gs drawer new-drawer)))

(def bottom-drawer (item/make ["drawer" "bottom drawer"]
                              "There was nothing special about it."
                              :closed true
                              :open {:pre true
                                     :say "A lot of silverware; utensils I didn’t knew existed. Also a box of candles."
                                     :post `post-open}
                              :items #{candles}))

(def dining (->
              (room/make "Dining room"
                         "A dining room with a table big enough to fit twenty people. There were ornate chairs all around it and a smaller table on the side. By the wall was a dinnerware cabinet with two drawers.")
              (room/add-item (item/make ["cabinet" "dinnerware cabinet"] "It had three shelves full of dinnerware and two drawers." :open "You mean a drawer?") "")
              (room/add-item top-drawer "")
              (room/add-item bottom-drawer "")
              (room/add-item (item/make ["dining table" "table"] "I lived in places smaller than that table.") "")
              (room/add-item (item/make ["table" "small table"] "I guessed it was used to hold dishes during dinner.") "")
              (room/add-item (item/make ["chair" "chairs"] "Dark wood matching the table. Arms and legs had elaborate carvings." :take "Those chairs were way too heavy to carry around.") "")))

(def piano (item/make "piano" "The lid was down and had a macramé tablecloth on top"
                      :take "Funny."
                      :open "Better left closed."
                      :play "I never learnt to play."
                      :use "I never learnt to play."))

; TODO react to using matches on fireplace?
(def tapestry (-> (room/make "Tapestries room"
                             "A living room with a huge tapestry covering the north wall, and two smaller ones on the opposite side, left and right of a door. There was a set of sofas around a fireplace, a drinks cabinet on a corner, and a piano.")
                  (room/add-item (item/make ["tapestry" "tapestries"] "All three depicted battles, historical events, I supposed." :take "I could carry infinite things in my bag, but not infinitely big things.") "")
                  (room/add-item piano "")
                  (room/add-item piano-stool "By the piano was a little stool.")
                  (room/add-item (item/make ["tablecloth" "macramé" "macramé tablecloth" "cloth"] "Some diamond pattern." :take "It was useless.") "")
                  (room/add-item (item/make "fireplace" "The fireplace was empty, it probably hadn't been lit in years.") "")
                  (room/add-item (item/make ["sofa" "sofas"] "There were three of them, the larger in front of the fireplace and too shorter ones on the sides." :use "I wasn't tired.") "")
                  (room/add-item (item/make ["drinks cabinet" "drinks" "drink" "cabinet"] "It had a wide selection of bottles and glasses of all sorts."
                                            :take "There was no drinking on the job."
                                            :drink "There was no drinking on the job."
                                            :open "There was no drinking on the job."
                                            :use "There was no drinking on the job.") "")))

(def pipe (item/make ["pipe" "briar pipe"] "The wood was shiny."
                     :take "I wasn't touching that thing, it probably had several generations of germs growing in it."
                     :use "I wasn't touching that thing, it probably had several generations of germs growing in it."))

(def parks-book (item/make ["book" "book on the table" "book on table"] "It was open a few pages before the end."
                           :take "No need to take it."
                           :read "I tasted the almost perverse pleasure of disengaging myself line by line from the things around me, and at the same time feeling my head rest comfort­ably on the green velvet of the chair with its high back, sensing that the cigarettes rested within reach of my hand, that beyond the great window the air of afternoon danced under the trees in the garden. Word by word, licked up by the sordid dilemma of the hero and heroine, letting myself be absorbed to the point where the images settled down and took on color and movement, I was witness to the final encounter in the mountain cabin. The woman ar­rived first, apprehensive; now the lover came in, his face cut by the backlash of a branch. Admirably, she stanched the blood with her kisses, but he rebuffed her caresses, he had not come to perform again the ceremonies of a secret passion, protected by a world of dry leaves and furtive paths through the forest. The dagger warmed itself against his chest, and underneath liberty pounded, hidden close. A lustful, panting dialogue raced down the pages like a rivu­let of snakes, and one felt it had all been decided from eternity. Even to those caresses which writhed about the lover's body, as though wishing to keep him there, to dissuade him from it; they sketched abominably the frame of that other body it was necessary to destroy. Nothing had been forgotten: alibis, unforeseen hazards, possible mis­takes. From this hour on, each instant had its use mi­nutely assigned. The cold-blooded, twice-gone-over re­examination of the details was barely broken off so that a hand could caress a cheek. It was beginning to get dark.\n\nNot looking at one another now, rigidly fixed upon the task which awaited them, they separated at the cabin door. She was to follow the trail that led north. On the path leading in the opposite direction, he turned for a mo­ment to watch her running, her hair loosened and flying. He ran in turn, crouching among the trees and hedges until, in the yellowish fog of dusk, he could distinguish the avenue of trees which led up to the house. The dogs were not supposed to bark, they did not bark. The estate manager would not be there at this hour, and he was not there. He went up the three porch steps and entered. The woman's words reached him over the thudding of blood in his ears: a long corridor with doors on each side, an oak door by the end, closed. The door of the library, and then, the knife in hand, the light from the great window, the high back of an armchair covered in green velvet, the head of the man in the chair reading a novel."))

(def armchair (item/make ["armchair" "green velvet armchair" "velvet armchair"]
                         "Green velvet. It had its back toward the door, as to avoid the possibility of an interruption."
                         :use "I wasn't tired."
                         :take "Way too heavy to carry around."))

(defn reveal-room [old gs]
  ;; need to manually remove the points because there are multiple verbs to do it
  ;; FIXME remove when synonyms implemented
  (let [portrait (utils/find-first gs "portrait")
        no-points (-> portrait
                      (update :pull dissoc :points)
                      (update :move dissoc :points))]
    (-> gs
        (utils/replace-item portrait no-points)
        (update-in [:room-map] room/connect :library :north :hidden-room))))

(def portrait (item/make ["portrait" "painting"]
                         "It depicted a man in that very same room, sitting on the green velvet armchair, smoking a pipe with a severe look in his eyes. It must have been painted by the end of the 19th century, judging by the clothes."
                         :pull {:pre true
                                :say "Pulling the frame of the portrait revealed it was fixed to a door that led to a hidden room."
                                :points 100
                                :post `reveal-room}
                         :move {:pre true
                                :say "Pulling the frame of the portrait revealed it was fixed to a door that led to a hidden room."
                                :points 100
                                :post `reveal-room}
                         :push "It didn't move in that direction."
                         :take "Kind of big to fit in my bag."))

(def shelves (item/make ["book" "books on the shelf" "books on shelf" "book on the shelf" "book on shelf" "books" "shelves" "shelf" "bookshelves" "bookcase"]
                        "More books than a man could possibly read in a lifetime."
                        :take "There were too many of them, better to use the catalog."))

(def library (->
              (room/make "Library"
                         "A library with bookshelves floor to ceiling, only interrupted by a window, the door to the corridor and a large portrait of a man."
                         :initial-description "A quick glimpse was enough to realize that the library was a prominent room in the house. The bookshelves went floor to ceiling on each wall, only interrupted by a window —notably larger than in other rooms—, the door to the corridor and a full-size portrait of a man on the northern wall.")
              (room/add-item catalog "Next to the door was a card cabinet.")
              (room/add-item (item/make ["side table" "table"] "On the side table were a briar pipe and an open book.") "Facing the window: a green velvet armchair with a side table.")
              (room/add-item (item/make "window" "The window was notably bigger than in the other rooms." :open "That was pointless.") "")
              (room/add-item pipe "")
              (room/add-item armchair "")
              (room/add-item portrait "")
              (room/add-item shelves "")
              (room/add-item parks-book "")))

(def hidden-room (->
                  (room/make "Hidden room" "An old and musty study."
                             :initial-description "The candlelight exposed an old study which was evidently excluded from the house cleaning routines; a musty smell and a thick layer of dust made it clear that no one had walked through the door in a long time."
                             :known true
                             :points 200
                             :dark true)
                  (room/add-item (item/make "desk" "It had one drawer.") "There was a wide desk in the center of the room and behind it a portrait of an old man.")
                  (room/add-item (item/make "door" "The same as in the other rooms.") "A door led east to the corridor.")
                  (room/add-item (item/make ["drawer" "desk drawer"] "Nothing special about it."
                                            :closed true :items #{key-set}) "")
                  (room/add-item safe-portrait "")
                  (room/add-item candlestick "On the desk were a candlestick and a book.")
                  (room/add-item (item/make ["book" "book on desk"]
                                            "The book was titled “The Conquest of the Desert”."
                                            :read "It was a history book about some 19th century military campaign: “The Conquest of the Desert”."
                                            :take "I had no plans to read it.") "")))

; TODO add mentioned items
; bedroom 2 and 3 are mirrored
(def base-bedroom (room/make "Bedroom"
                             "An uninhabited bedroom. It had a wide bed and a night table, an armoire, a desk with its chair. A single window mildly lighted the room."
                             :initial-description "The bedroom was impeccably clean and far from being empty, yet I got the clear impression that it hadn't been occupied in years… It had a wide bed and a night table, an armoire, a desk with its chair. A single window mildly lighted the room."))

(defn back-bedroom-post
   "The intial description of the second mirror entered room references the other."
   [old gs]
   (let [current (:current-room gs)
         otherkw (get {:bedroom2 :bedroom3 :bedroom3 :bedroom2} current)]
      (-> gs
         (assoc-in [:room-map otherkw :initial-description] "The room was a perfect mirror of the one across the hall: same furniture, same immaculacy and lifelessness.")
         ; drop conditions
         (assoc-in [:room-map :back-hall3 :west] :bedroom2)
         (assoc-in [:room-map :back-hall3 :east] :bedroom3))))

(def bedroom2 (assoc base-bedroom :id :bedroom2))
(def bedroom3 (assoc base-bedroom :id :bedroom3))
