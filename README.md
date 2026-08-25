# Taka Rune Journal

Taka Rune Journal is a Kotlin Multiplatform / Compose Multiplatform rune reading and journaling app.

It is a working prototype built as a personal product and technical learning project to deepen my hands-on experience with the modern Kotlin mobile ecosystem. The app focuses on tactile rune readings, fully offline interpretation content, local persistence, reusable Compose UI components, custom visual assets, and a polished mobile user experience.

## Overview

Taka lets users create rune readings, save them, add personal notes, and revisit them through a timeline. The app combines structured reading flows with reflective journaling, using a calm visual style and a focused local-first architecture.

Rune reading is a reflective practice using symbols from historical runic alphabets as prompts for interpretation and journaling. Traditionally, drawing runes can involve choosing stones from a pouch or cloth bag, creating a tactile sense of agency and ritual. Taka aims to translate that feeling into a mobile interaction through gesture, haptics, animation, and a glowing rune reveal.

All rune interpretation content is available offline. The app does not rely on a network request or external generation step to produce readings; interpretations are stored as structured app resources and selected based on the rune, orientation, spread position, and chosen reading topic.

The project is Android-first for now, with Kotlin Multiplatform structure in place.

## Current Features

* New reading flow with spread selection, topic selection, and an optional question
* Single Rune and Past, Present, Future reading flows
* Fully offline rune interpretation library
* Hundreds of structured interpretation entries across runes, orientations, spread positions, and topics
* Shake-to-mix rune drawing interaction with haptic feedback
* Drag, move, and rotate runes before reveal
* Animated rune reveal sequence where runes glow and then cool off
* Reading interpretation screens with tabs
* Topic-specific rune interpretation support
* Upright and reversed rune handling
* Editable notes for saved readings
* Timeline of rune readings and journal entries
* Journal entry creation and detail screens
* Local persistence for saved readings and journal entries
* Shared Compose Multiplatform resources for strings and drawables
* Reusable Compose UI components and app-level design tokens
* Custom rune, brand, and UI assets
* Android-first development, with KMP/iOS structure in place

## Reading System

Taka includes a fully offline reading system. Interpretations are not generated on demand; they are written, structured, and bundled with the app.

The reading system supports different topics, including:

* General
* Relationships
* Purpose
* Security
* Self

Each reading can combine several layers of meaning:

* the rune itself
* upright or reversed orientation
* the selected reading topic
* the spread position, such as Past, Present, or Future
* contextual keywords

Because interpretations vary by topic and orientation, the app contains hundreds of carefully written and reviewed reading entries. AI tools helped accelerate research, drafting, comparison, and iteration, while the final interpretation structure, tone, wording, omissions, and quality decisions were reviewed and refined manually.

Current reading work focuses on improving interpretation quality across topics, orientations, spreads, and keywords so the readings feel understandable, specific, and meaningful rather than generic.

## User Experience

The app is designed to feel calm, intentional, and tactile.

The rune drawing interaction is designed to avoid feeling like a simple random button press. Users can shake the phone to mix the runes, with haptic feedback suggesting stones clinking together, then drag, move, and rotate the runes before revealing them. The reveal sequence uses haptics and animation as each rune glows and then cools off, translating some of the physical feeling of drawing rune stones from a pouch or cloth bag into a mobile interaction.

Recent UX work includes:

* A guided new-reading setup flow
* A dedicated draw screen with haptics and animation
* A rune reveal animation with glow and cooling effects
* Clear separation between reading creation and reading interpretation
* Editable notes with view and edit modes
* Empty states for notes and timeline content
* A small reusable design system for cards, buttons, text actions, text fields, spacing, and theme colors
* iOS-style Title Case for short UI labels, with sentence case for descriptions and body text

## Tech Stack

* Kotlin Multiplatform
* Compose Multiplatform
* Compose Resources
* Coroutines and Flow
* Koin
* Room for local persistence
* MVVM-style presentation layer
* Feature-based UI structure
* Clean separation between UI, domain, and data concerns

## Architecture

The app is organized around feature-specific UI and shared domain/data concepts where appropriate.

The reading flow is separated into two main areas:

* `NewReading...` screens and state for creating a new reading
* `ReadingInterpretation...` screens and state for viewing an existing reading

This separation keeps the reading setup/draw flow independent from the saved reading interpretation flow.

The app uses explicit UI state models, ViewModels, and UI events for navigation and one-time effects. Local UI state is kept inside composables when it only affects temporary presentation, while persisted reading and journal data is handled through the ViewModel and data layer.

The interpretation library is structured through shared Compose resources, allowing the app to select the correct offline text based on the saved reading data.

## Design System

Taka includes a small reusable Compose UI layer, including components such as:

* `TakaScaffold`
* `TakaCard`
* `TakaButton`
* `TakaTextAction`
* `TakaTextField`
* shared spacing tokens
* shared color and shape definitions

The visual style is intentionally minimal: soft grayscale surfaces, subtle borders, low elevation, and a muted slate / blue-gray action color.

## Visual Assets

The app includes custom rune and brand assets created and refined specifically for the project.

Asset work included:

* rune stone images for the reading experience
* glowing rune reveal states
* a custom Taka symbol
* app logo / wordmark exploration
* simple monochrome vector icons for app actions
* transparent and theme-friendly drawable resources

AI tools helped with asset generation and visual iteration, but the final direction, selection, cleanup, consistency checks, and integration into the app were reviewed manually. This was a significant part of the project because the rune drawing and reveal experience depends heavily on the assets feeling coherent, tactile, and polished.

## AI-Assisted Development

I used AI tools heavily as a development accelerator while keeping architecture, product direction, code review, and final implementation decisions under my own control.

I used AI as a technical sparring partner rather than as an autonomous code owner.

AI helped with boilerplate, unfamiliar APIs, debugging, implementation options, asset generation, content iteration, and learning velocity. It was especially useful for interaction-heavy UI work such as the tactile rune drawing screen, where gesture handling, drawing behavior, layout details, haptic feedback, and animation timing needed careful iteration.

AI also supported the development of the offline interpretation library by helping compare meanings, generate draft variations, and surface wording options across hundreds of rune/topic/orientation combinations.

The app structure, UX decisions, technical judgment, final content decisions, and quality standards remained human-led.

## Status

Working prototype.

The main application flow is usable, including reading creation, topic selection, rune drawing, local persistence, timeline browsing, interpretation screens, and editable notes. The major interaction and asset work is now in place, including the haptic drawing experience and animated rune reveal.

The main remaining time-consuming work is refining the quality of the offline rune interpretation library across topics, orientations, spreads, and keywords.