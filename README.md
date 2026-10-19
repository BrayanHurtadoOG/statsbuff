# Stats-buff

Mod de Minecraft **Forge 1.20.1** (Forge 47.4.x) creado con MCreator 2026.2 + código Java custom.

## Requisitos

- JDK 17
- MCreator 2026.2 con el plugin **Minecraft Forge 1.20.1 Generator** (de _Spectrall_) para los elementos visuales

## Compilar

Con Gradle 9.6.1 instalado:

```bash
gradle build
```

(El `gradle-wrapper.jar` no se versiona; si lo necesitas, cópialo desde tu workspace local de MCreator.)

El `.jar` queda en `build/libs/`. También se compila automáticamente en cada push (GitHub Actions) y el jar queda como artefacto del workflow.

## Flujo de trabajo

- Los **elementos visuales** (bloques, items, etc.) se editan en MCreator.
- El **código Java custom** vive en `src/main/java` (archivos nuevos o secciones custom bloqueadas).
- Commitea con MCreator **cerrado** y haz `pull` **antes** de abrir MCreator, para no pisar cambios.
