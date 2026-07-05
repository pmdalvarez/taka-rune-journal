package com.taka.runejournal.core.domain.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import taka_rune_journal.composeapp.generated.resources.Res
import taka_rune_journal.composeapp.generated.resources.ic_topic_general_icon
import taka_rune_journal.composeapp.generated.resources.reading_topic_general_description
import taka_rune_journal.composeapp.generated.resources.reading_topic_general_name
import taka_rune_journal.composeapp.generated.resources.reading_topic_purpose_description
import taka_rune_journal.composeapp.generated.resources.reading_topic_purpose_name
import taka_rune_journal.composeapp.generated.resources.reading_topic_relationships_description
import taka_rune_journal.composeapp.generated.resources.reading_topic_relationships_name
import taka_rune_journal.composeapp.generated.resources.reading_topic_security_description
import taka_rune_journal.composeapp.generated.resources.reading_topic_security_name
import taka_rune_journal.composeapp.generated.resources.reading_topic_self_description
import taka_rune_journal.composeapp.generated.resources.reading_topic_self_name

enum class ReadingTopic(
  val key: String,
  val icon: DrawableResource,
  val title: StringResource,
  val description: StringResource
) {
  GENERAL(
    key = "general",
    icon = Res.drawable.ic_topic_general_icon,
    title = Res.string.reading_topic_general_name,
    description = Res.string.reading_topic_general_description
  ),
  RELATIONSHIPS(
    key = "relationships",
    icon = Res.drawable.ic_topic_general_icon,
    title = Res.string.reading_topic_relationships_name,
    description = Res.string.reading_topic_relationships_description
    ),
  PURPOSE(
    key = "purpose",
    icon = Res.drawable.ic_topic_general_icon,
    title = Res.string.reading_topic_purpose_name,
    description = Res.string.reading_topic_purpose_description
    ),
  SECURITY(
    key = "security",
    icon = Res.drawable.ic_topic_general_icon,
    title = Res.string.reading_topic_security_name,
    description = Res.string.reading_topic_security_description
  ),
  SELF(
    key = "self",
    icon = Res.drawable.ic_topic_general_icon,
    title = Res.string.reading_topic_self_name,
    description = Res.string.reading_topic_self_description
  );

  companion object {
    fun fromKey(key: String): ReadingTopic? =
      ReadingTopic.entries.firstOrNull { it.key == key }
  }
}
/************************************************************
General
Open reflection

Relationships
Love, family, friendship, connection

Purpose
Work, direction, calling, meaningful effort

Security
Money, stability, home, resources

Self
Inner life, growth, wellbeing, identity




Self

Self is for questions about inner state, identity, intuition, emotions, and personal clarity.

Use it when the user is asking:

“What am I feeling?”
“What do I need to understand about myself?”
“What part of me needs attention?”

This category makes the rune meanings more introspective. A rune like Kenaz, for example, becomes less about external skill and more about inner illumination, self-knowledge, or seeing something clearly within yourself.

Relationships

Relationships is for love, friendship, family, trust, communication, attachment, reciprocity, and emotional connection.

Use it when the user is asking:

“What is happening between us?”
“How should I approach this relationship?”
“What kind of connection is this?”

This category shifts rune meanings toward giving/receiving, communication, loyalty, boundaries, repair, attraction, distance, and mutual support.

Purpose

Purpose is for work, calling, direction, ambition, contribution, creativity, and meaningful effort.

Use it when the user is asking:

“What should I focus on?”
“What is my next step?”
“What kind of work or path is calling me?”

This category makes rune meanings more action-oriented. A rune may point toward discipline, momentum, craft, leadership, persistence, or the kind of contribution the user wants to make.

Security

Security is for money, stability, home, resources, safety, health of foundations, and practical support.

Use it when the user is asking:

“What do I need to feel stable?”
“What should I know about money or resources?”
“How can I protect my foundation?”

This category grounds rune meanings in material reality: finances, shelter, routines, energy reserves, family support, and risk management.
 ************************************************************/