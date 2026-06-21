# Contextual Semantic Compression

This document records the first experimental formulation of Alseth as a contextual semantic compression engine.

## Core intuition

A compact sign does not need to contain the whole idea.

A cross on a wall is visually tiny, but it can point to Christianity, scripture, ritual, sacrifice, resurrection, ethics, community and history. The sign itself is not the whole content. It is an address. The surrounding interpretive system is the decompression key.

Alseth's semantic layer models that pattern in code.

## Byte layout

The first prototype uses one byte split into two nibbles:

```txt
DDDD SSSS
```

- `SSSS`: four analyzable state bits.
- `DDDD`: four contextual dimension bits.

A state bit does not have a fixed meaning by itself. Its meaning is recovered by combining:

1. the value of that state bit;
2. the active dimensional bits;
3. the presence or absence of nearby state bits;
4. a shared contextual schema.

## Difference from ordinary bitmask permissions

Traditional bitmask systems usually work like this:

```txt
bit 0 = READ
bit 1 = WRITE
bit 2 = DELETE
```

In the semantic compression model, the same bit can expand differently:

```txt
state bit 0 = 1 + dimension bit 0 = 1  -> cross as Christian symbol
state bit 0 = 1 + dimension bit 1 = 1  -> cross as geometric mark
state bit 0 = 0 + state bit 1 = 1      -> meaningful absence modified by neighbor state
state bit 0 = 0 + state bit 1 = 0      -> pure absence
```

The point is not merely to store permissions. The point is to store a small address that can expand into a larger meaning through a schema.

## Implemented classes

- `EightBitSemanticSymbol`: stores the compact 8-bit symbol and exposes state/dimension nibbles.
- `SemanticRule`: defines when a state bit points to a meaning.
- `SemanticMeaning`: stores the expanded meaning key, summary and optional payload.
- `ContextualSemanticSchema`: shared decompression key.
- `SemanticCompressor`: facade for creating and expanding symbols.

## Current limits

This is not yet general intelligence, semantic reasoning, or arbitrary compression of human meaning.

It is a small formal experiment: compact symbols plus explicit contextual rules.

The next hard question is whether this model can represent something more useful than a lookup table. That requires real use cases, collisions, benchmarks and failures.
