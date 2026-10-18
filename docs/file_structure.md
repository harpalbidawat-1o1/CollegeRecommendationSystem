# File Structure

```text
CollegeRecommendationSystem/  (the opened workspace root)
|-- README.md
|-- .gitignore
|-- lib/                       Optional local libraries; no dependency included
|-- sql/                       Supplied database SQL scripts
|   |-- schema.sql             Placeholder for the approved schema, when supplied
|   `-- sample_data.sql        Placeholder; no fabricated records
|-- docs/                      Project requirements and structure notes
|   |-- requirements.md
|   `-- file_structure.md
|-- src/com/college/            Java application package root
|   |-- Main.java               Application entry point
|   |-- db/                     Database configuration and JDBC connection
|   |-- model/                  College, course, and cutoff data models
|   |-- dao/                    College and cutoff data access operations
|   |-- service/                Recommendation-related application logic
|   `-- gui/                    Home, search, details, and admin Swing windows
|-- compile.bat                 Windows compilation launcher placeholder
`-- run.bat                     Windows application launcher placeholder
```

All Java source files and batch scripts are empty placeholders for now. The `lib` directory is reserved for a JDBC driver if one is added later.