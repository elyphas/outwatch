package outwatch.dom

import cats.Applicative
import cats.effect.Effect

trait OutwatchDsl[F[+_]] extends Styles[F] with Tags[F] with Attributes[F] { thisDsl =>
  implicit def effectF: Effect[F]
  implicit def applicativeF: Applicative[F] = effectF

  type VNode = VNodeF[F]
  type VDomModifier = VDomModifierF[F]

  object tags extends Tags[F] with TagBuilder[F] {
    implicit val effectF: Effect[F] = thisDsl.effectF
    object extra extends TagsExtra[F] with TagBuilder[F] {
      implicit val effectF: Effect[F] = thisDsl.effectF
    }
  }
  object attributes extends Attributes[F] {
    object attrs extends Attrs[F]
    object reflected extends ReflectedAttrs[F]
    object props extends Props[F]
    object events extends Events
    object outwatch extends OutwatchAttributes[F]
    object lifecycle extends OutWatchLifeCycleAttributes
  }
  object events {
    object window extends WindowEvents
    object document extends DocumentEvents
  }
}

