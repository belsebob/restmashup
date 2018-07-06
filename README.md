
Music Rest Mashup is a REST API which consists of a mashup of three underlying API:s 
MusicBrainz provides an API which delivers information of artists (name, year of birth, country, etc.). 
Wikipedia is a wiki from which description of artists is collected. 
Cover Art Archive is a sister project to MusicBrainz it provides links to cover art to albums

Music Rest Mashup takes a MusicBrain mbid as a parameter, at / path. Three services collects data from the external API:s, mashes the result up and delivers it to the client.
Running application: 
cd to project, run command: mvn jetty:run


Known issues: 
The external API:s are slow, this is mitigated partially by cache:ing previously made requests.
