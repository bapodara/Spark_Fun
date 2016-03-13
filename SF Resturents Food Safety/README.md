The Health Department has developed an inspection report and scoring system. After conducting an inspection of the facility, the Health Inspector calculates a score based on the violations observed. The data is shared by city at: https://data.sfgov.org/Health-and-Social-Services/Restaurant-Scores/stya-26eb

This is scala programs written on top op apache spark to perform following analysis:
1. What is the inspection score distribution like? (inspections_plus.csv)
2. What is the risk category distribution like? (violations_plus.csv)
3. Which 20 businesses got lowest scores? (inspections_plus.csv, businesses_plus.csv)
4. Which 20 businesses got highest score? (inspections_plus.csv, businesses_plus.csv)
5. Among all the restaurants that got 100 score, what kind of violations did they get (if any)?
6. Average inspection score by zip code
7. Proportion of all businesses in each neighborhood (zip code) that have incurred at least one of the violations on this list
    * "High risk vermin infestation"
    * "Moderate risk vermin infestation"
    * "Sewage or wastewater contamination”
    * "Improper food labeling or menu misrepresentation"
    * "Contaminated or adulterated food”
    * "Reservice of previously served foods"
    * "Expected output: zip code, percentage"
8. Are SF restaurants clean? Justify your answer
9. Leverage map feature in spark-notework to plot the business by longitude and latitude