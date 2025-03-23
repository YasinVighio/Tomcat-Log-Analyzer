## Misc
Archive folder added in screens package but excluded, the screen AccessLogViewer and AccessLogViewScreen contain
same content. AccessLogViewer uses GridBagLayout, which makes difficult to maintain UI therefore created file with
same contents to maintain and enhance UI (AccessLogViewScreen)

## Building Artifact
1. JAR artifact can be built using Java 8+
2. configs folder which contain app.ini must be present in same directory as of build